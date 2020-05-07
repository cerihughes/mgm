package uk.co.cerihughes.mgm.model.input

import com.google.gson.annotations.SerializedName

class GoogleSheetsString {
    @SerializedName("\$t")
    private val value: String? = null
    fun resolvedValue(): String? {
        return if (value == null || value.length == 0) {
            null
        } else value
    }
}
