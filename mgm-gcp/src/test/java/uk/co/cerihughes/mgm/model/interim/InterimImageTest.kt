package uk.co.cerihughes.mgm.model.interim

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class InterimImageTest {
    companion object {
        private const val SIZE = 64
        private const val GOOD_SIZE_STRING = "300"
        private const val BAD_SIZE_STRING = "5.6"
        private const val URL_DATA = "URL DATA"
    }

    @Test
    fun builderWithGoodData() {
        val image = InterimImage(SIZE, URL_DATA)
        Assertions.assertNotNull(image)
        Assertions.assertEquals(SIZE, image.size)
        Assertions.assertEquals(URL_DATA, image.url)
    }

    @Test
    fun builderWithGoodSizeString() {
        val image = InterimImage(GOOD_SIZE_STRING.toIntOrNull(), URL_DATA)
        Assertions.assertNotNull(image)
        Assertions.assertEquals(300, image.size)
        Assertions.assertEquals(URL_DATA, image.url)
    }

    @Test
    fun builderWithBadSizeString() {
        val image = InterimImage(BAD_SIZE_STRING.toIntOrNull(), URL_DATA)
        Assertions.assertNotNull(image)
        Assertions.assertNull(image.size)
        Assertions.assertEquals(URL_DATA, image.url)
    }

    @Test
    fun builderWithoutSize() {
        val image = InterimImage(null, URL_DATA)
        Assertions.assertNotNull(image)
        Assertions.assertNull(image.size)
        Assertions.assertEquals(URL_DATA, image.url)
    }
}