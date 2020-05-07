package uk.co.cerihughes.mgm.translate;

import org.openapitools.model.AlbumApiModel;
import uk.co.cerihughes.mgm.model.interim.InterimAlbum;

public interface AlbumTranslation extends EntityTranslation {
    AlbumApiModel translate(InterimAlbum interimAlbum);
}
