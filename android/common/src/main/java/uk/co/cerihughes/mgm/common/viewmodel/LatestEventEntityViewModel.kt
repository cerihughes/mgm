package uk.co.cerihughes.mgm.common.viewmodel

import uk.co.cerihughes.mgm.common.model.Album
import uk.co.cerihughes.mgm.common.model.AlbumType
import uk.co.cerihughes.mgm.common.model.Image
import uk.co.cerihughes.mgm.common.model.Playlist

class LatestEventEntityViewModel(images: List<Image>, val entityType: String, val entityName: String, val entityOwner: String, val spotifyURL: String?) : AlbumArtViewModel(images) {

    companion object {
        fun createEntityViewModel(album: Album): LatestEventEntityViewModel {
            val entityType = when (album.type) {
                AlbumType.CLASSIC -> "CLASSIC ALBUM"
                AlbumType.NEW -> "NEW ALBUM"
                else -> "ALBUM"
            }

            val spotifyURL = album.spotifyId?.let {
                SpotifyURLGenerator.createSpotifyAlbumURL(it)
            }

            return LatestEventEntityViewModel(album.images, entityType, album.name, album.artist, spotifyURL)
        }

        fun createEntityViewModel(playlist: Playlist): LatestEventEntityViewModel {
            val spotifyURL = playlist.spotifyId?.let {
                SpotifyURLGenerator.createSpotifyPlaylistURL(it)
            }

            return LatestEventEntityViewModel(playlist.images, "PLAYLIST", playlist.name, playlist.owner, spotifyURL)
        }
    }
}