package uk.co.cerihughes.mgm.model.interim

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

private const val PLAYLILST_DATA = "PLAYLIST DATA"

internal class InterimPlaylistTest {
    @Test
    fun builderWithGoodData() {
        val playlist = InterimPlaylist(PLAYLILST_DATA)
        Assertions.assertNotNull(playlist)
        Assertions.assertEquals(PLAYLILST_DATA, playlist.playlistData)
    }
}