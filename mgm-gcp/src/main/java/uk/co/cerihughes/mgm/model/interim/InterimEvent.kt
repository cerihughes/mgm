package uk.co.cerihughes.mgm.model.interim

data class InterimEvent(
    val number: Int,
    val date: String? = null,
    val classicAlbum: InterimAlbum,
    val newAlbum: InterimAlbum,
    val playlist: InterimPlaylist? = null,
    val location: InterimLocation? = null
)
