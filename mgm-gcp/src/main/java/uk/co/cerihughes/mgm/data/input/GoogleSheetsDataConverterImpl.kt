package uk.co.cerihughes.mgm.data.input

import com.google.gson.Gson
import uk.co.cerihughes.mgm.model.input.GoogleSheetsEntry
import uk.co.cerihughes.mgm.model.input.GoogleSheetsModel
import uk.co.cerihughes.mgm.model.interim.InterimAlbum
import uk.co.cerihughes.mgm.model.interim.InterimEvent
import uk.co.cerihughes.mgm.model.interim.InterimPlaylist
import uk.co.cerihughes.mgm.model.output.AlbumApiModel

class GoogleSheetsDataConverterImpl : GoogleSheetsDataConverter {
    private val gson = Gson()
    override fun convert(json: String): List<InterimEvent>? {
        val model = deserialise(json) ?: return null
        val feed = model.feed ?: return null
        return feed
                .resolvedEntries()
                .mapNotNull { createEvent(it) }
    }

    private fun deserialise(json: String): GoogleSheetsModel? {
        return try {
            gson.fromJson(json, GoogleSheetsModel::class.java)
        } catch (_: Exception) {
            null
        }
    }

    private fun createEvent(entry: GoogleSheetsEntry): InterimEvent? {
        val eventId = entry.resolvedId()?.toIntOrNull() ?: return null
        val classicAlbum = createClassicAlbum(entry) ?: return null
        val newAlbum = createNewAlbum(entry) ?: return null
        return InterimEvent(eventId,
                entry.resolvedDate(),
                classicAlbum,
                newAlbum,
                createPlaylist(entry))
    }

    private fun createClassicAlbum(entry: GoogleSheetsEntry): InterimAlbum? {
        return createAlbum(AlbumApiModel.TypeEnum.CLASSIC,
                entry.resolvedClassicScore(),
                entry.resolvedClassicAlbum())
    }

    private fun createNewAlbum(entry: GoogleSheetsEntry): InterimAlbum? {
        return createAlbum(AlbumApiModel.TypeEnum.NEW,
                entry.resolvedNewScore(),
                entry.resolvedNewAlbum())
    }

    private fun createAlbum(type: AlbumApiModel.TypeEnum, score: String?, albumData: String?): InterimAlbum? {
        if (albumData == null || albumData.trim().isEmpty()) {
            return null
        }

        return InterimAlbum(type, albumData, score?.toFloatOrNull())
    }

    private fun createPlaylist(entry: GoogleSheetsEntry): InterimPlaylist? {
        val playlistData = entry.resolvedPlaylist()
        if (playlistData == null || playlistData.trim().isEmpty()) {
            return null;
        }
        return InterimPlaylist(playlistData)
    }
}