package uk.co.cerihughes.mgm.translate.spotify

import se.michaelthelin.spotify.SpotifyApi
import se.michaelthelin.spotify.model_objects.specification.Playlist
import uk.co.cerihughes.mgm.model.interim.InterimEvent
import uk.co.cerihughes.mgm.model.interim.InterimPlaylist
import uk.co.cerihughes.mgm.model.output.PlaylistApiModel
import uk.co.cerihughes.mgm.translate.PlaylistTranslation

class SpotifyPlaylistTranslation(private val spotifyApi: SpotifyApi) : SpotifyTranslation(), PlaylistTranslation {
    private val getPlaylistOperation = GetPlaylistOperation()
    private val preprocessedPlaylists: MutableMap<String, Playlist> = HashMap()
    override fun preprocess(interimEvents: List<InterimEvent>) {
        preprocessPlaylists(interimEvents)
    }

    protected fun preprocessPlaylists(interimEvents: List<InterimEvent?>) {
        val playlistIds = interimEvents
            .mapNotNull { it?.playlist?.playlistData }
        if (playlistIds.isEmpty()) {
            return
        }

        // For now, just process the last playlist as the app only renders the most recent event.
        // The Spotify web API currently doesn't support batch gets on a playlist, so this saves unnecessary network
        // calls too.
        val playlistId = playlistIds[playlistIds.size - 1]
        val playlist = getPlaylistOperation.execute(spotifyApi, playlistId)
        if (playlist != null) {
            preprocessedPlaylists[playlistId] = playlist
        }
    }

    override fun translate(interimPlaylist: InterimPlaylist): PlaylistApiModel? {
        val spotifyId = interimPlaylist.playlistData
        if (isValidData(spotifyId) == false) {
            return null
        }
        val spotifyPlaylist = preprocessedPlaylists[spotifyId] ?: return null
        val spotifyUser = spotifyPlaylist.owner ?: return null
        val name = spotifyPlaylist.name
        val owner = spotifyUser.displayName
        val model = PlaylistApiModel()
        model.spotifyId = spotifyId
        model.name = name
        model.owner = owner
        model.images = getImages(spotifyPlaylist.images)
        return model
    }
}
