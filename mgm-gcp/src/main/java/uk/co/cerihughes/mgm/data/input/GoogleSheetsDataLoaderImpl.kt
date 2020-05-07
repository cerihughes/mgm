package uk.co.cerihughes.mgm.data.input

import java.io.IOException
import org.apache.http.HttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils

class GoogleSheetsDataLoaderImpl : GoogleSheetsDataLoader {
    companion object {
        private const val url = "https://spreadsheets.google.com/feeds/list/1AeTjG2coPkJ_3XSsSUWmu1Pb5Tpm9YdHP3M3iBtgUew/od6/public/values?alt=json"
    }

    override fun loadJsonData(): String? {
        try {
            HttpClientBuilder.create().build().use { httpClient ->
                val httpGet = HttpGet(url)
                return httpClient.execute(httpGet) { response: HttpResponse ->
                    val status = response.statusLine.statusCode
                    return@execute if (status in 200..299) {
                        response.entity?.let { EntityUtils.toString(it) }
                    } else {
                        null
                    }
                }
            }
        } catch (ex: IOException) {
            return null
        }
    }
}
