package uk.co.cerihughes.mgm.translate

import java.util.*
import uk.co.cerihughes.mgm.model.interim.InterimAlbum
import uk.co.cerihughes.mgm.model.interim.InterimEvent
import uk.co.cerihughes.mgm.model.interim.InterimPlaylist
import uk.co.cerihughes.mgm.model.output.AlbumApiModel
import uk.co.cerihughes.mgm.model.output.EventApiModel
import uk.co.cerihughes.mgm.model.output.LocationApiModel
import uk.co.cerihughes.mgm.model.output.PlaylistApiModel

class DataTranslation {
    companion object {
        private var chapterArtsCenter = createLocation("Chapter Arts Center", 51.4829773, -3.2056947)
        private var tenFeetTall = createLocation("10 Feet Tall", 51.4805194, -3.1810703)
        private var craftyDevilsCellar = createLocation("Crafty Devil's Cellar", 51.48227690, -3.20186570)
        private var stayAtHome = createLocation("#stayathome", 0.0, 0.0)

        private fun createLocation(name: String, latitude: Double, longitude: Double): LocationApiModel {
            val model = LocationApiModel()
            model.name = name
            model.latitude = latitude
            model.longitude = longitude
            return model
        }
    }

    private val albumTranslations: MutableList<AlbumTranslation> = ArrayList()
    private val playlistTranslations: MutableList<PlaylistTranslation> = ArrayList()
    fun addAlbumTranslation(translation: AlbumTranslation) {
        albumTranslations.add(translation)
    }

    fun addPlaylistTranslation(tranlsation: PlaylistTranslation) {
        playlistTranslations.add(tranlsation)
    }

    fun translate(interimEvents: List<InterimEvent>): List<EventApiModel> {
        (albumTranslations + playlistTranslations).forEach { it.preprocess(interimEvents) }
        return interimEvents
                .mapNotNull { translate(it) }
    }

    private fun translate(interimEvent: InterimEvent): EventApiModel {
        val model = EventApiModel()
        model.number = interimEvent.number
        model.date = interimEvent.date
        model.classicAlbum = translate(interimEvent.classicAlbum)
        model.newAlbum = translate(interimEvent.newAlbum)
        model.location = translateLocation(interimEvent.number)
        model.playlist = interimEvent.playlist?.let { translate(it) }
        return model
    }

    private fun translate(interimAlbum: InterimAlbum): AlbumApiModel? {
        return albumTranslations
                .mapNotNull { it.translate(interimAlbum) }
                .firstOrNull()
    }

    private fun translate(interimPlaylist: InterimPlaylist): PlaylistApiModel? {
        return playlistTranslations
                .mapNotNull { it.translate(interimPlaylist) }
                .firstOrNull()
    }

    private fun translateLocation(number: Int): LocationApiModel? {
        return when (number) {
            in 1..45 -> chapterArtsCenter
            in 46..48 -> tenFeetTall
            in 74..75 -> stayAtHome
            else -> craftyDevilsCellar
        }
    }
}
