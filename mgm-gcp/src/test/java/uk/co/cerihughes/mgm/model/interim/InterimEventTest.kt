package uk.co.cerihughes.mgm.model.interim

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import uk.co.cerihughes.mgm.model.output.AlbumApiModel

class InterimEventTest {
    companion object {
        private const val NUMBER = 2
        private const val DATE = "13/11/2018"
        private val ALBUM = InterimAlbum(AlbumApiModel.TypeEnum.CLASSIC, "DATA")
        private val PLAYLIST = InterimPlaylist("DATA")
    }

    @Test
    fun builderWithGoodData() {
        val event = InterimEvent(NUMBER, DATE, ALBUM, ALBUM, PLAYLIST)
        Assertions.assertNotNull(event)
        Assertions.assertEquals(NUMBER, event.number)
        Assertions.assertEquals(DATE, event.date)
        Assertions.assertEquals(ALBUM, event.classicAlbum)
        Assertions.assertEquals(ALBUM, event.newAlbum)
        Assertions.assertEquals(PLAYLIST, event.playlist)
    }

    @Test
    fun builderWithoutDate() {
        val event = InterimEvent(NUMBER, null, ALBUM, ALBUM, PLAYLIST)
        Assertions.assertNotNull(event)
        Assertions.assertEquals(NUMBER, event.number)
        Assertions.assertNull(event.date)
        Assertions.assertEquals(ALBUM, event.classicAlbum)
        Assertions.assertEquals(ALBUM, event.newAlbum)
        Assertions.assertEquals(PLAYLIST, event.playlist)
    }

    @Test
    fun builderWithoutPlaylist() {
        val event = InterimEvent(NUMBER, DATE, ALBUM, ALBUM)
        Assertions.assertNotNull(event)
        Assertions.assertEquals(NUMBER, event.number)
        Assertions.assertEquals(DATE, event.date)
        Assertions.assertEquals(ALBUM, event.classicAlbum)
        Assertions.assertEquals(ALBUM, event.newAlbum)
        Assertions.assertNull(event.playlist)
    }
}
