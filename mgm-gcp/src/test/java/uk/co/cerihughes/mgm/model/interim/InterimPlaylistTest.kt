package uk.co.cerihughes.mgm.model.interim

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class InterimPlaylistTest {
    companion object {
        private const val PLAYLILST_DATA = "PLAYLIST DATA"
    }

    @Test
    fun builderWithGoodData() {
        val playlist = InterimPlaylist(PLAYLILST_DATA)
        Assertions.assertNotNull(playlist)
        Assertions.assertEquals(PLAYLILST_DATA, playlist.playlistData)
    }
}
