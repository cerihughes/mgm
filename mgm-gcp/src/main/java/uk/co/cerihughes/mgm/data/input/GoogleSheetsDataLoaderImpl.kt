package uk.co.cerihughes.mgm.data.input

import com.google.api.client.http.HttpExecuteInterceptor
import com.google.api.client.http.HttpRequest
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.sheets.v4.Sheets
import uk.co.cerihughes.mgm.Secrets
import java.io.IOException

class GoogleSheetsDataLoaderImpl : GoogleSheetsDataLoader {
    companion object {
        private val jsonFactory: JsonFactory = GsonFactory.getDefaultInstance()
    }

    private fun getSheets(): Sheets {
        val transport = NetHttpTransport.Builder().build()
        val httpRequestInitializer = HttpRequestInitializer { request: HttpRequest ->
            request.interceptor = HttpExecuteInterceptor { intercepted: HttpRequest ->
                intercepted.url["key"] = Secrets.googleSheetsApiKey
            }
        }
        return Sheets.Builder(transport, jsonFactory, httpRequestInitializer)
            .build()
    }

    private fun getValues(): List<List<Any?>?>? {
        try {
            return getSheets()
                .spreadsheets()
                .values()[Secrets.googleSheetsId, "Sheet1!A2:H"]
                .execute()
                .getValues()
        } catch (ex: IOException) {
            return null
        }
    }

    override fun loadSheetsData(): List<List<String>>? {
        val outerList = getValues() ?: emptyList<List<String>>()
        return outerList
            .mapNotNull { innerList ->
                innerList?.mapNotNull { it?.toString() }
            }
    }
}
