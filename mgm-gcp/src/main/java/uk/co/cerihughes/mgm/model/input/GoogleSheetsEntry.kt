package uk.co.cerihughes.mgm.model.input

import com.google.gson.annotations.SerializedName

class GoogleSheetsEntry {
    @SerializedName("gsx\$id")
    private val id: GoogleSheetsString? = null

    @SerializedName("gsx\$date")
    private val date: GoogleSheetsString? = null

    @SerializedName("gsx\$playlist")
    private val playlist: GoogleSheetsString? = null

    @SerializedName("gsx\$classicalbum")
    private val classicAlbum: GoogleSheetsString? = null

    @SerializedName("gsx\$classicscore")
    private val classicScore: GoogleSheetsString? = null

    @SerializedName("gsx\$newalbum")
    private val newAlbum: GoogleSheetsString? = null

    @SerializedName("gsx\$newscore")
    private val newScore: GoogleSheetsString? = null

    @SerializedName("gsx\$location")
    private val location: GoogleSheetsString? = null

    fun resolvedId() = resolve(id)
    fun resolvedDate() = resolve(date)
    fun resolvedPlaylist() = resolve(playlist)
    fun resolvedClassicAlbum() = resolve(classicAlbum)
    fun resolvedClassicScore() = resolve(classicScore)
    fun resolvedNewAlbum() = resolve(newAlbum)
    fun resolvedNewScore() = resolve(newScore)
    fun resolvedLocation() = resolve(location)

    private fun resolve(string: GoogleSheetsString?): String? {
        return string?.resolvedValue()
    }
}
