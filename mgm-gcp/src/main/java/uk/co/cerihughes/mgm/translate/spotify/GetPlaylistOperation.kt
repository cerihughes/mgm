package uk.co.cerihughes.mgm.translate.spotify

import com.wrapper.spotify.SpotifyApi
import com.wrapper.spotify.model_objects.specification.Playlist
import java.io.IOException

class GetPlaylistOperation {
    fun execute(spotifyApi: SpotifyApi, playlistId: String): Playlist? {
        return try {
            val getPlaylistRequest = spotifyApi.getPlaylist(playlistId).build()
            getPlaylistRequest.execute()
        } catch (e: IOException) {
            null
        }
    }
}
