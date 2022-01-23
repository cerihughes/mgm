package uk.co.cerihughes.mgm.translate.spotify

import com.neovisionaries.i18n.CountryCode
import se.michaelthelin.spotify.SpotifyApi
import se.michaelthelin.spotify.model_objects.specification.Album
import java.util.*

class GetAlbumsOperation {
    fun execute(spotifyApi: SpotifyApi, albumIds: List<String>): List<Album> {
        val input = albumIds.toMutableList()
        val output = mutableListOf<Album>()
        while (input.size > 0) {
            val start = 0
            val end = Math.min(input.size, 20)
            val range = input.subList(start, end)
            val array = range.toTypedArray()
            val results = executeBatch(spotifyApi, *array)
            output.addAll(results)
            range.clear()
        }
        return output
    }

    private fun executeBatch(spotifyApi: SpotifyApi, vararg albumIds: String): List<Album> {
        return try {
            val getSeveralAlbumsRequest = spotifyApi.getSeveralAlbums(*albumIds)
                .market(CountryCode.GB)
                .build()
            Arrays.asList(*getSeveralAlbumsRequest.execute())
        } catch (e: Exception) {
            emptyList()
        }
    }
}
