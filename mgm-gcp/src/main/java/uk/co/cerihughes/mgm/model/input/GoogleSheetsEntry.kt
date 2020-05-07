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
    fun resolvedId(): String? {
        return resolve(id)
    }

    fun resolvedDate(): String? {
        return resolve(date)
    }

    fun resolvedPlaylist(): String? {
        return resolve(playlist)
    }

    fun resolvedClassicAlbum(): String? {
        return resolve(classicAlbum)
    }

    fun resolvedClassicScore(): String? {
        return resolve(classicScore)
    }

    fun resolvedNewAlbum(): String? {
        return resolve(newAlbum)
    }

    fun resolvedNewScore(): String? {
        return resolve(newScore)
    }

    private fun resolve(string: GoogleSheetsString?): String? {
        return string?.resolvedValue()
    }
}