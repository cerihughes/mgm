package uk.co.cerihughes.mgm.translate;

import org.openapitools.model.PlaylistApiModel;
import uk.co.cerihughes.mgm.model.interim.InterimPlaylist;

public interface PlaylistTranslation extends EntityTranslation {
    PlaylistApiModel translate(InterimPlaylist interimPlaylist);
}
