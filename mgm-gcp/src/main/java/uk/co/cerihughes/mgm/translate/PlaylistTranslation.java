package uk.co.cerihughes.mgm.translate;

import uk.co.cerihughes.mgm.model.interim.InterimPlaylist;
import uk.co.cerihughes.mgm.model.output.PlaylistApiModel;

public interface PlaylistTranslation extends EntityTranslation {
    PlaylistApiModel translate(InterimPlaylist interimPlaylist);
}
