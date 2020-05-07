package uk.co.cerihughes.mgm.translate

import org.openapitools.model.AlbumApiModel
import org.openapitools.model.ImageApiModel

fun createAlbumApiModel(
        type: AlbumApiModel.TypeEnum,
        name: String,
        artist: String,
        spotifyId: String? = null,
        score: Float? = null,
        images: List<ImageApiModel>? = null

): AlbumApiModel {
    val album = AlbumApiModel()
    album.type = type
    album.name = name
    album.artist = artist
    album.spotifyId = spotifyId
    album.score = score
    album.images = images
    return album
}

