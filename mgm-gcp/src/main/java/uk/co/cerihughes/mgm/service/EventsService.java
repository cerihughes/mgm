package uk.co.cerihughes.mgm.service;

import uk.co.cerihughes.mgm.data.input.GoogleSheetsDataConverterImpl;
import uk.co.cerihughes.mgm.data.input.GoogleSheetsDataLoaderImpl;
import uk.co.cerihughes.mgm.model.interim.InterimEvent;
import uk.co.cerihughes.mgm.model.output.OutputEvent;
import uk.co.cerihughes.mgm.translate.DataTranslation;
import uk.co.cerihughes.mgm.translate.googlesheets.GoogleSheetsTranslationFactory;
import uk.co.cerihughes.mgm.translate.spotify.SpotifyTranslationFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.util.List;

@Path("/")
public class EventsService {

    private static final int SECONDS_IN_HOUR = 60 * 60;
    private static final long MILLIS_IN_HOUR = 1000 * SECONDS_IN_HOUR;

    @GET
    @Path("/mgm.json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response events(@Context Request request) throws IOException {
        CacheControl cc = new CacheControl();
        cc.setMaxAge(SECONDS_IN_HOUR);

        long expires = getLastModified() + MILLIS_IN_HOUR;
        EntityTag etag = new EntityTag(String.valueOf(expires));

        Response.ResponseBuilder rb = request.evaluatePreconditions(etag);
        if (rb != null) {
            return rb.cacheControl(cc)
                    .header(HttpHeaders.EXPIRES, expires)
                    .tag(etag)
                    .build();
        }

        rb = Response.ok(allEvents())
                .cacheControl(cc)
                .header(HttpHeaders.EXPIRES, expires)
                .tag(etag);
        return rb.build();
    }

    private List<OutputEvent> allEvents() throws IOException {
        final GoogleSheetsDataLoaderImpl loader = new GoogleSheetsDataLoaderImpl();
        final String input = loader.loadJsonData();

        final GoogleSheetsDataConverterImpl converter = new GoogleSheetsDataConverterImpl();
        final List<InterimEvent> interimEvents = converter.convert(input);

        final DataTranslation translation = new DataTranslation();
        final SpotifyTranslationFactory spotifyTranslationFactory = new SpotifyTranslationFactory();
        spotifyTranslationFactory.generateToken();

        translation.addAlbumTranslation(GoogleSheetsTranslationFactory.createAlbumTranslation());
        translation.addAlbumTranslation(spotifyTranslationFactory.createAlbumTranslation());
        translation.addPlaylistTranslation(spotifyTranslationFactory.createPlaylistTranslation());

        return translation.translate(interimEvents);
    }

    private long getLastModified() {
        long now = System.currentTimeMillis();
        long mod = now % MILLIS_IN_HOUR;
        return now - mod;
    }
}
