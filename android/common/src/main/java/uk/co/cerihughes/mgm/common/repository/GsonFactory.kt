package uk.co.cerihughes.mgm.common.repository

import com.google.gson.Gson
import com.google.gson.GsonBuilder

class GsonFactory {
    companion object {
        fun createGson(): Gson {
            return GsonBuilder().setDateFormat("dd/MM/yyyy").create()
        }
    }
}