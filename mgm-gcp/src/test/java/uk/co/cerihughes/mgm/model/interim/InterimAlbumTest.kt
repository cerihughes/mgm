package uk.co.cerihughes.mgm.model.interim

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import uk.co.cerihughes.mgm.model.output.AlbumApiModel

private val ALBUM_TYPE = AlbumApiModel.TypeEnum.CLASSIC
private const val ALBUM_DATA = "ALBUM DATA"
private const val ALBUM_SCORE = 5.6f

class InterimAlbumTest {
    @Test
    fun builderWithGoodData() {
        val album = InterimAlbum(ALBUM_TYPE, ALBUM_DATA, ALBUM_SCORE)
        Assertions.assertNotNull(album)
        Assertions.assertEquals(ALBUM_TYPE, album.type)
        Assertions.assertEquals(ALBUM_DATA, album.albumData)
        Assertions.assertEquals(ALBUM_SCORE, album.score)
    }

    @Test
    fun builderWithoutScore() {
        val album = InterimAlbum(ALBUM_TYPE, ALBUM_DATA)
        Assertions.assertNotNull(album)
        Assertions.assertEquals(ALBUM_TYPE, album.type)
        Assertions.assertEquals(ALBUM_DATA, album.albumData)
        Assertions.assertNull(album.score)
    }
}