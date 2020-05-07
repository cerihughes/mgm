package uk.co.cerihughes.mgm.translate.spotify;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.specification.Album;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import org.openapitools.model.AlbumApiModel;
import uk.co.cerihughes.mgm.model.interim.InterimAlbum;
import uk.co.cerihughes.mgm.model.interim.InterimEvent;
import uk.co.cerihughes.mgm.translate.AlbumTranslation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class SpotifyAlbumTranslation extends SpotifyTranslation implements AlbumTranslation {
    private final SpotifyApi spotifyApi;

    private final GetAlbumsOperation getAlbumsOperation = new GetAlbumsOperation();
    private Map<String, Album> preprocessedAlbums = new HashMap<>();

    public SpotifyAlbumTranslation(SpotifyApi spotifyApi) {
        super();

        this.spotifyApi = spotifyApi;
    }

    @Override
    public void preprocess(List<InterimEvent> interimEvents) {
        if (interimEvents == null || interimEvents.size() == 0) {
            return;
        }

        preprocessAlbums(interimEvents);
    }

    protected void preprocessAlbums(List<InterimEvent> interimEvents) {
        final ArrayList<InterimAlbum> interimAlbums = new ArrayList<>();
        for (InterimEvent interimEvent : interimEvents) {
            InterimAlbum classicAlbum = interimEvent.getClassicAlbum();
            InterimAlbum newAlbum = interimEvent.getNewAlbum();
            if (classicAlbum != null) {
                interimAlbums.add(classicAlbum);
            }
            if (newAlbum != null) {
                interimAlbums.add(newAlbum);
            }

        }
        final List<String> albumIds = interimAlbums.stream()
                .map(InterimAlbum::getAlbumData)
                .filter(s -> isValidData(s))
                .collect(Collectors.toList());
        final List<Album> albums = getAlbumsOperation.execute(spotifyApi, albumIds);
        preprocessedAlbums = albums.stream()
                .collect(Collectors.toMap(Album::getId, Function.identity()));
    }

    @Override
    public AlbumApiModel translate(InterimAlbum interimAlbum) {
        final String spotifyId = interimAlbum.getAlbumData();
        if (isValidData(spotifyId) == false) {
            return null;
        }

        final AlbumApiModel.TypeEnum type = interimAlbum.getType();
        if (type == null || spotifyId == null) {
            return null;
        }

        final Album spotifyAlbum = preprocessedAlbums.get(spotifyId);
        if (spotifyAlbum == null) {
            return null;
        }

        final ArtistSimplified[] spotifyArtists = spotifyAlbum.getArtists();
        if (spotifyArtists == null || spotifyArtists.length == 0) {
            return null;
        }

        final ArtistSimplified spotifyArtist = spotifyArtists[0];
        final String name = spotifyAlbum.getName();
        final String artist = spotifyArtist.getName();
        final AlbumApiModel album = new AlbumApiModel();
        album.setType(type);
        album.setSpotifyId(spotifyId);
        album.setName(name);
        album.setArtist(artist);
        album.setScore(interimAlbum.getScore());
        album.setImages(getImages(spotifyAlbum.getImages()));
        return album;
    }
}