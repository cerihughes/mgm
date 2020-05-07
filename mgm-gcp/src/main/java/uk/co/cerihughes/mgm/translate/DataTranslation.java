package uk.co.cerihughes.mgm.translate;

import org.openapitools.model.AlbumApiModel;
import org.openapitools.model.EventApiModel;
import org.openapitools.model.LocationApiModel;
import org.openapitools.model.PlaylistApiModel;
import uk.co.cerihughes.mgm.model.interim.InterimAlbum;
import uk.co.cerihughes.mgm.model.interim.InterimEvent;
import uk.co.cerihughes.mgm.model.interim.InterimPlaylist;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class DataTranslation {
    private final List<AlbumTranslation> albumTranslations = new ArrayList<>();
    private final List<PlaylistTranslation> playlistTranslations = new ArrayList<>();

    public void addAlbumTranslation(AlbumTranslation translation) {
        albumTranslations.add(translation);
    }

    public void addPlaylistTranslation(PlaylistTranslation tranlsation) {
        playlistTranslations.add(tranlsation);
    }

    public List<EventApiModel> translate(List<InterimEvent> interimEvents) {
        Stream.concat(albumTranslations.stream(), playlistTranslations.stream())
                .forEach(e -> e.preprocess(interimEvents));

        return interimEvents.stream()
                .map(e -> translate(e))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private EventApiModel translate(InterimEvent interimEvent) {
        final AlbumApiModel classicAlbum = translate(interimEvent.getClassicAlbum());
        final AlbumApiModel newAlbum = translate(interimEvent.getNewAlbum());
        final PlaylistApiModel playlist = translate(interimEvent.getPlaylist());

        EventApiModel model = new EventApiModel();
        model.setNumber(interimEvent.getNumber());
        model.setDate(interimEvent.getDate());
        model.setClassicAlbum(classicAlbum);
        model.setNewAlbum(newAlbum);
        model.setLocation(translateLocation());
        model.setPlaylist(playlist);
        return model;
    }

    private AlbumApiModel translate(InterimAlbum interimAlbum) {
        if (interimAlbum == null) {
            return null;
        }

        for (AlbumTranslation albumTranslation : albumTranslations) {
            AlbumApiModel outputAlbum = albumTranslation.translate(interimAlbum);
            if (outputAlbum != null) {
                return outputAlbum;
            }
        }

        return null;
    }

    private PlaylistApiModel translate(InterimPlaylist interimPlaylist) {
        if (interimPlaylist == null) {
            return null;
        }

        for (PlaylistTranslation playlistTranslation : playlistTranslations) {
            PlaylistApiModel outputPlaylist = playlistTranslation.translate(interimPlaylist);
            if (outputPlaylist != null) {
                return outputPlaylist;
            }
        }

        return null;
    }

    private LocationApiModel translateLocation() {
        final String name = "Crafty Devil's Cellar";
        final double latitude = 51.48227690;
        final double longitude = -3.20186570;

        LocationApiModel model = new LocationApiModel();
        model.setName(name);
        model.setLatitude(latitude);
        model.setLongitude(longitude);
        return model;
    }
}

