package uk.co.cerihughes.mgm.model.input

import com.google.gson.annotations.SerializedName

class GoogleSheetsAlbum {
    @SerializedName("name")
    val name: String? = null

    @SerializedName("artist")
    val artist: String? = null

    @SerializedName("images")
    val images: List<GoogleSheetsImage>? = null
        get() = field ?: emptyList()

}