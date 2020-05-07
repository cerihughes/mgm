package uk.co.cerihughes.mgm.translate

import org.openapitools.model.AlbumApiModel
import org.openapitools.model.EventApiModel
import org.openapitools.model.LocationApiModel
import org.openapitools.model.PlaylistApiModel
import uk.co.cerihughes.mgm.model.interim.InterimAlbum
import uk.co.cerihughes.mgm.model.interim.InterimEvent
import uk.co.cerihughes.mgm.model.interim.InterimPlaylist
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream

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
        Stream.concat(albumTranslations.stream(), playlistTranslations.stream())
                .forEach { e: EntityTranslation -> e.preprocess(interimEvents) }
        return interimEvents.stream()
                .map { e: InterimEvent -> translate(e) }
                .filter { obj: EventApiModel? -> Objects.nonNull(obj) }
                .collect(Collectors.toList())
    }

    private fun translate(interimEvent: InterimEvent): EventApiModel {
        val model = EventApiModel()
        model.number = interimEvent.number
        model.date = interimEvent.date
        model.classicAlbum = translate(interimEvent.classicAlbum)
        model.newAlbum = translate(interimEvent.newAlbum)
        model.location = translateLocation()
        model.playlist = translate(interimEvent.playlist)
        return model
    }

    private fun translate(interimAlbum: InterimAlbum?): AlbumApiModel? {
        if (interimAlbum == null) {
            return null
        }
        for (albumTranslation in albumTranslations) {
            val outputAlbum = albumTranslation.translate(interimAlbum)
            if (outputAlbum != null) {
                return outputAlbum
            }
        }
        return null
    }

    private fun translate(interimPlaylist: InterimPlaylist?): PlaylistApiModel? {
        if (interimPlaylist == null) {
            return null
        }
        for (playlistTranslation in playlistTranslations) {
            val outputPlaylist = playlistTranslation.translate(interimPlaylist)
            if (outputPlaylist != null) {
                return outputPlaylist
            }
        }
        return null
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