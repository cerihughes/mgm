package uk.co.cerihughes.mgm.translate.spotify

import com.wrapper.spotify.SpotifyApi
import com.wrapper.spotify.model_objects.specification.Album
import uk.co.cerihughes.mgm.model.interim.InterimAlbum
import uk.co.cerihughes.mgm.model.interim.InterimEvent
import uk.co.cerihughes.mgm.model.output.AlbumApiModel
import uk.co.cerihughes.mgm.translate.AlbumTranslation

class SpotifyAlbumTranslation(private val spotifyApi: SpotifyApi) : SpotifyTranslation(), AlbumTranslation {
    private val getAlbumsOperation = GetAlbumsOperation()
    private var preprocessedAlbums: Map<String, Album> = HashMap()
    override fun preprocess(interimEvents: List<InterimEvent>) {
        preprocessAlbums(interimEvents)
    }

    protected fun preprocessAlbums(interimEvents: List<InterimEvent>) {
        val interimAlbums = interimEvents
            .flatMap { listOf(it.classicAlbum, it.newAlbum) }
        val albumIds = interimAlbums
            .map { it.albumData }
            .filter { isValidData(it) }
        val albums = getAlbumsOperation.execute(spotifyApi, albumIds)
        preprocessedAlbums = albums.map { it.id to it }
            .toMap()
    }

    override fun translate(interimAlbum: InterimAlbum): AlbumApiModel? {
        val spotifyId = interimAlbum.albumData
        if (!isValidData(spotifyId)) {
            return null
        }

        val spotifyAlbum = preprocessedAlbums[spotifyId] ?: return null
        val spotifyArtists = spotifyAlbum.artists
        if (spotifyArtists == null || spotifyArtists.size == 0) {
            return null
        }
        val spotifyArtist = spotifyArtists[0]
        val name = spotifyAlbum.name
        val artist = spotifyArtist.name
        val album = AlbumApiModel()
        album.type = interimAlbum.type
        album.spotifyId = spotifyId
        album.name = name
        album.artist = artist
        album.score = interimAlbum.score
        album.images = getImages(spotifyAlbum.images)
        return album
    }
}
