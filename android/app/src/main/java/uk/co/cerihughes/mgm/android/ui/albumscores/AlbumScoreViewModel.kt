package uk.co.cerihughes.mgm.android.ui.albumscores

import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import uk.co.cerihughes.mgm.android.model.Album
import uk.co.cerihughes.mgm.android.ui.AlbumArtViewModel

class AlbumScoreViewModel(private val album: Album, private val index: Int) : AlbumArtViewModel(album.images) {

    private val award: AlbumAward

    init {
        award = AlbumAward.createAward(album.score ?: 0.0f)
    }

    fun albumName(): String {
        return album.name
    }

    fun artistName(): String {
        return album.artist
    }

    fun rating(): String {
        val score = album.score ?: return ""
        return String.format("%.01f", score)
    }

    @ColorInt
    fun ratingColour(): Int {
        return award.colour
    }

    @DrawableRes
    fun awardImage(): Int {
        return award.drawable
    }

    fun position(): String {
        return String.format("%d", index + 1)
    }
}