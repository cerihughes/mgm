package uk.co.cerihughes.mgm.translate.spotify

import com.wrapper.spotify.SpotifyApi
import com.wrapper.spotify.exceptions.SpotifyWebApiException
import uk.co.cerihughes.mgm.Secrets
import uk.co.cerihughes.mgm.translate.AlbumTranslation
import uk.co.cerihughes.mgm.translate.PlaylistTranslation
import java.io.IOException

class SpotifyTranslationFactory {
    @Throws(IOException::class)
    fun generateToken() {
        val accessToken = generateAccessToken(spotifyApi)
        spotifyApi.accessToken = accessToken
    }

    fun createAlbumTranslation(): AlbumTranslation {
        return SpotifyAlbumTranslation(spotifyApi)
    }

    fun createPlaylistTranslation(): PlaylistTranslation {
        return SpotifyPlaylistTranslation(spotifyApi)
    }

    @Throws(IOException::class)
    private fun generateAccessToken(spotifyApi: SpotifyApi): String {
        val clientCredentialsRequest = spotifyApi.clientCredentials().build()
        return try {
            val clientCredentials = clientCredentialsRequest.execute()
            clientCredentials.accessToken
        } catch (e: SpotifyWebApiException) {
            throw IOException(e)
        }
    }

    companion object {
        private val spotifyApi = SpotifyApi.Builder()
            .setClientId(Secrets.spotifyApiClientId)
            .setClientSecret(Secrets.spotifyApiClientSecret)
            .build()
    }
}
