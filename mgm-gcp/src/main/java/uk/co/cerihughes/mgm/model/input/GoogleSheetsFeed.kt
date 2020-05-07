package uk.co.cerihughes.mgm.model.input

import com.google.gson.annotations.SerializedName

class GoogleSheetsFeed {
    @SerializedName("entry")
    private val entries: List<GoogleSheetsEntry>? = null
    fun resolvedEntries(): List<GoogleSheetsEntry> {
        return entries ?: emptyList()
    }
}
