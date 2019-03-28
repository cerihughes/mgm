package uk.co.cerihughes.mgm.common.model

import com.google.gson.annotations.SerializedName

enum class AlbumType {
    @SerializedName("classic")
    CLASSIC,

    @SerializedName("new")
    NEW
}