package uk.co.cerihughes.mgm.translate.spotify

import com.wrapper.spotify.model_objects.specification.Image
import org.openapitools.model.ImageApiModel

abstract class SpotifyTranslation {
    protected fun isValidData(data: String?): Boolean {
        return data != null && data.length == 22
    }

    protected fun getImages(spotifyImages: Array<Image>?): List<ImageApiModel>? {
        return if (spotifyImages == null || spotifyImages.size == 0) {
            null
        } else spotifyImages.mapNotNull { createOutputImage(it) }
    }

    private fun createOutputImage(spotifyImage: Image): ImageApiModel? {
        val width = spotifyImage.width
        val height = spotifyImage.height
        val url = spotifyImage.url
        if (width == null && height == null) {
            return null
        }
        val w = width ?: 0
        val h = height ?: 0
        val size = Math.max(w, h)
        val model = ImageApiModel()
        model.size = size
        model.url = url
        return model
    }
}