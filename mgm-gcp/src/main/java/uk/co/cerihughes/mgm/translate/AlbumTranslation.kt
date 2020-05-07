package uk.co.cerihughes.mgm.translate

import uk.co.cerihughes.mgm.model.interim.InterimAlbum
import uk.co.cerihughes.mgm.model.output.AlbumApiModel

interface AlbumTranslation : EntityTranslation {
    fun translate(interimAlbum: InterimAlbum): AlbumApiModel?
}
