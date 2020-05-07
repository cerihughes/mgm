package uk.co.cerihughes.mgm.model.interim

import uk.co.cerihughes.mgm.model.output.AlbumApiModel

data class InterimAlbum(
    val type: AlbumApiModel.TypeEnum,
    val albumData: String,
    val score: Float? = null
)
