package uk.co.cerihughes.mgm.translate.googlesheets

import uk.co.cerihughes.mgm.translate.AlbumTranslation

object GoogleSheetsTranslationFactory {
    @JvmStatic
    fun createAlbumTranslation(): AlbumTranslation {
        return GoogleSheetsAlbumTranslation()
    }
}