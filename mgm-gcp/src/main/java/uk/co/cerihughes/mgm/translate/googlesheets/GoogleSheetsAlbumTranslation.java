package uk.co.cerihughes.mgm.translate.googlesheets;

import com.google.gson.Gson;
import org.openapitools.model.AlbumApiModel;
import org.openapitools.model.ImageApiModel;
import uk.co.cerihughes.mgm.model.input.GoogleSheetsAlbum;
import uk.co.cerihughes.mgm.model.input.GoogleSheetsImage;
import uk.co.cerihughes.mgm.model.interim.InterimAlbum;
import uk.co.cerihughes.mgm.model.interim.InterimEvent;
import uk.co.cerihughes.mgm.translate.AlbumTranslation;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GoogleSheetsAlbumTranslation implements AlbumTranslation {
    private final Gson gson = new Gson();

    private GoogleSheetsAlbum deserialise(String json) {
        return gson.fromJson(json, GoogleSheetsAlbum.class);
    }

    @Override
    public void preprocess(List<InterimEvent> interimEvents) {
        // No-op
    }

    @Override
    public AlbumApiModel translate(InterimAlbum interimAlbum) {
        try {
            final GoogleSheetsAlbum album = deserialise(interimAlbum.getAlbumData());
            final AlbumApiModel model = new AlbumApiModel();
            model.setType(interimAlbum.getType());
            model.setName(album.getName());
            model.setArtist(album.getArtist());
            model.setScore(interimAlbum.getScore());
            model.setImages(getImages(album.getImages()));
            return model;
        } catch (Exception e) {
            return null;
        }
    }

    private List<ImageApiModel> getImages(List<GoogleSheetsImage> images) {
        if (images == null || images.isEmpty()) {
            return null;
        }

        return images.stream()
                .map(i -> createOutputImage(i))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private ImageApiModel createOutputImage(GoogleSheetsImage image) {
        final Integer size = image.getSize();
        final String url = image.getUrl();

        ImageApiModel model = new ImageApiModel();
        model.setSize(size);
        model.setUrl(url);
        return model;
    }
}