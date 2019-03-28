package uk.co.cerihughes.mgm.common.viewmodel

class LatestEventEntityViewModel(images: List<uk.co.cerihughes.mgm.common.model.Image>, val entityType: String, val entityName: String, val entityOwner: String, val spotifyURL: String?) : AlbumArtViewModel(images) {

    companion object {
        fun createEntityViewModel(album: uk.co.cerihughes.mgm.common.model.Album): LatestEventEntityViewModel {
            val entityType = when (album.type) {
                uk.co.cerihughes.mgm.common.model.AlbumType.CLASSIC -> "CLASSIC ALBUM"
                uk.co.cerihughes.mgm.common.model.AlbumType.NEW -> "NEW ALBUM"
                else -> "ALBUM"
            }

            val spotifyURL = album.spotifyId?.let {
                SpotifyURLGenerator.createSpotifyAlbumURL(it)
            }

            return LatestEventEntityViewModel(
                album.images,
                entityType,
                album.name,
                album.artist,
                spotifyURL
            )
        }

        fun createEntityViewModel(playlist: uk.co.cerihughes.mgm.common.model.Playlist): LatestEventEntityViewModel {
            val spotifyURL = playlist.spotifyId?.let {
                SpotifyURLGenerator.createSpotifyPlaylistURL(it)
            }

            return LatestEventEntityViewModel(
                playlist.images,
                "PLAYLIST",
                playlist.name,
                playlist.owner,
                spotifyURL
            )
        }
    }
}