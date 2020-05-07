package uk.co.cerihughes.mgm.translate.googlesheets

import com.google.gson.Gson
import uk.co.cerihughes.mgm.model.input.GoogleSheetsAlbum
import uk.co.cerihughes.mgm.model.input.GoogleSheetsImage
import uk.co.cerihughes.mgm.model.interim.InterimAlbum
import uk.co.cerihughes.mgm.model.interim.InterimEvent
import uk.co.cerihughes.mgm.model.output.AlbumApiModel
import uk.co.cerihughes.mgm.model.output.ImageApiModel
import uk.co.cerihughes.mgm.translate.AlbumTranslation

class GoogleSheetsAlbumTranslation : AlbumTranslation {
    private val gson = Gson()
    private fun deserialise(json: String): GoogleSheetsAlbum? {
        return try {
            gson.fromJson(json, GoogleSheetsAlbum::class.java)
        } catch (e: Exception) {
            null
        }
    }

    override fun preprocess(interimEvents: List<InterimEvent>) {
        // No-op
    }

    override fun translate(interimAlbum: InterimAlbum): AlbumApiModel? {
        val album = deserialise(interimAlbum.albumData) ?: return null
        val model = AlbumApiModel()
        model.type = interimAlbum.type
        model.name = album.name
        model.artist = album.artist
        model.score = interimAlbum.score
        model.images = getImages(album.images)
        return model
    }

    private fun getImages(images: List<GoogleSheetsImage>?): List<ImageApiModel>? {
        return if (images == null || images.isEmpty()) {
            null
        } else images.mapNotNull { createOutputImage(it) }
    }

    private fun createOutputImage(image: GoogleSheetsImage): ImageApiModel {
        val size = image.size
        val url = image.url
        val model = ImageApiModel()
        model.size = size
        model.url = url
        return model
    }
}