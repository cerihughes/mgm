package uk.co.cerihughes.mgm.data.input

import uk.co.cerihughes.mgm.model.interim.InterimAlbum
import uk.co.cerihughes.mgm.model.interim.InterimEvent
import uk.co.cerihughes.mgm.model.interim.InterimLocation
import uk.co.cerihughes.mgm.model.interim.InterimPlaylist
import uk.co.cerihughes.mgm.model.output.AlbumApiModel

class GoogleSheetsDataConverterImpl : GoogleSheetsDataConverter {
    override fun convert(data: List<List<String>>): List<InterimEvent>? {
        return data.mapNotNull { createEvent(it) }
    }

    private fun createEvent(entry: List<String>): InterimEvent? {
        val eventId = entry.resolvedId()?.toIntOrNull() ?: return null
        val classicAlbum = createClassicAlbum(entry) ?: return null
        val newAlbum = createNewAlbum(entry) ?: return null
        return InterimEvent(
            eventId,
            entry.resolvedDate(),
            classicAlbum,
            newAlbum,
            createPlaylist(entry),
            createLocation(entry)
        )
    }

    private fun createClassicAlbum(entry: List<String>): InterimAlbum? {
        return createAlbum(
            AlbumApiModel.TypeEnum.CLASSIC,
            entry.resolvedClassicScore(),
            entry.resolvedClassicAlbum()
        )
    }

    private fun createNewAlbum(entry: List<String>): InterimAlbum? {
        return createAlbum(
            AlbumApiModel.TypeEnum.NEW,
            entry.resolvedNewScore(),
            entry.resolvedNewAlbum()
        )
    }

    private fun createAlbum(type: AlbumApiModel.TypeEnum, score: String?, albumData: String?): InterimAlbum? {
        if (albumData == null || albumData.trim().isEmpty()) {
            return null
        }

        return InterimAlbum(type, albumData, score?.toFloatOrNull())
    }

    private fun createPlaylist(entry: List<String>): InterimPlaylist? {
        val playlistData = entry.resolvedPlaylist()
        if (playlistData == null || playlistData.trim().isEmpty()) {
            return null
        }
        return InterimPlaylist(playlistData)
    }

    private fun createLocation(entry: List<String>): InterimLocation? {
        val locationData = entry.resolvedLocation()
        if (locationData == null || locationData.trim().isEmpty()) {
            return null
        }
        return InterimLocation(locationData)
    }
}

private fun List<String>.resolvedId(): String? = getOrNull(0)
private fun List<String>.resolvedDate(): String? = getOrNull(1)
private fun List<String>.resolvedPlaylist(): String? = getOrNull(2)
private fun List<String>.resolvedClassicScore(): String? = getOrNull(3)
private fun List<String>.resolvedClassicAlbum(): String? = getOrNull(4)
private fun List<String>.resolvedNewScore(): String? = getOrNull(5)
private fun List<String>.resolvedNewAlbum(): String? = getOrNull(6)
private fun List<String>.resolvedLocation(): String? = getOrNull(7)