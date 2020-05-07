package uk.co.cerihughes.mgm.translate

import uk.co.cerihughes.mgm.model.interim.InterimPlaylist
import uk.co.cerihughes.mgm.model.output.PlaylistApiModel

interface PlaylistTranslation : EntityTranslation {
    fun translate(interimPlaylist: InterimPlaylist): PlaylistApiModel?
}