package uk.co.cerihughes.mgm.translate

import uk.co.cerihughes.mgm.model.interim.InterimAlbum
import uk.co.cerihughes.mgm.model.interim.InterimEvent
import uk.co.cerihughes.mgm.model.interim.InterimPlaylist
import uk.co.cerihughes.mgm.model.output.AlbumApiModel
import uk.co.cerihughes.mgm.model.output.EventApiModel
import uk.co.cerihughes.mgm.model.output.LocationApiModel
import uk.co.cerihughes.mgm.model.output.PlaylistApiModel
import java.util.*

class DataTranslation {
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
        model.location = translateLocation()
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

    private fun translateLocation(): LocationApiModel? {
        return null
//        val name = "Crafty Devil's Cellar"
//        val latitude = 51.48227690
//        val longitude = -3.20186570
//        val model = LocationApiModel()
//        model.name = name
//        model.latitude = latitude
//        model.longitude = longitude
//        return model
    }
}