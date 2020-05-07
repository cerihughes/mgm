package uk.co.cerihughes.mgm.translate;

import uk.co.cerihughes.mgm.model.interim.InterimAlbum;
import uk.co.cerihughes.mgm.model.output.AlbumApiModel;

public interface AlbumTranslation extends EntityTranslation {
    AlbumApiModel translate(InterimAlbum interimAlbum);
}
