package uk.co.cerihughes.mgm.resource

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import uk.co.cerihughes.mgm.data.input.GoogleSheetsDataConverterImpl
import uk.co.cerihughes.mgm.data.input.GoogleSheetsDataLoaderImpl
import uk.co.cerihughes.mgm.model.output.EventApiModel
import uk.co.cerihughes.mgm.translate.DataTranslation
import uk.co.cerihughes.mgm.translate.googlesheets.GoogleSheetsTranslationFactory.createAlbumTranslation
import uk.co.cerihughes.mgm.translate.spotify.SpotifyTranslationFactory
import java.io.IOException
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.*

@Path("/")
@Api(value = "/")
class EventResource {
    companion object {
        private val SECONDS_IN_HOUR = 60 * 60
        private val MILLIS_IN_HOUR = 1000 * SECONDS_IN_HOUR.toLong()
    }

    @GET
    @Path("/mgm.json")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Get all events", response = EventApiModel::class, responseContainer = "List")
    @Throws(IOException::class)
    fun events(@Context request: Request): Response {
        val cc = CacheControl()
        cc.maxAge = SECONDS_IN_HOUR
        val expires = lastModified + MILLIS_IN_HOUR
        val etag = EntityTag(expires.toString())
        val rb = request.evaluatePreconditions(etag)
        if (rb != null) {
            return rb.cacheControl(cc)
                    .header(HttpHeaders.EXPIRES, expires)
                    .tag(etag)
                    .build()
        }

        val payload = allEvents()
        val responseCode = if (payload == null) Response.serverError() else Response.ok(payload)
        return responseCode
                .cacheControl(cc)
                .header(HttpHeaders.EXPIRES, expires)
                .tag(etag)
                .build()
    }

    private fun allEvents(): List<EventApiModel>? {
        val loader = GoogleSheetsDataLoaderImpl()
        val input = loader.loadJsonData() ?: return null

        val converter = GoogleSheetsDataConverterImpl()
        val interimEvents = converter.convert(input) ?: return null

        val translation = DataTranslation()
        val spotifyTranslationFactory = SpotifyTranslationFactory()
        spotifyTranslationFactory.generateToken()
        translation.addAlbumTranslation(createAlbumTranslation())
        translation.addAlbumTranslation(spotifyTranslationFactory.createAlbumTranslation())
        translation.addPlaylistTranslation(spotifyTranslationFactory.createPlaylistTranslation())
        return translation.translate(interimEvents)
    }

    private val lastModified: Long
        private get() {
            val now = System.currentTimeMillis()
            val mod = now % MILLIS_IN_HOUR
            return now - mod
        }
}