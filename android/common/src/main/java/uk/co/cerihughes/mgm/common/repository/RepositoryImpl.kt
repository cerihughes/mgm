package uk.co.cerihughes.mgm.common.repository

import uk.co.cerihughes.mgm.common.repository.local.LocalDataSource
import uk.co.cerihughes.mgm.common.repository.remote.RemoteDataSource

class RepositoryImpl(private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource):
    Repository {

    private val gson = GsonFactory.createGson()

    private var cachedEvents: List<uk.co.cerihughes.mgm.common.model.Event>? = null

    override fun getEvents(callback: Repository.GetEventsCallback) {
        cachedEvents?.let {
            callback.onEventsLoaded(it)
        } ?: loadEvents(callback)
    }

    private fun loadEvents(callback: Repository.GetEventsCallback) {
        remoteDataSource.getRemoteData(object: RemoteDataSource.GetRemoteDataCallback {
            override fun onDataLoaded(data: String) {
                localDataSource.persistRemoteData(data)
                val events = gson.fromJson(data , Array<uk.co.cerihughes.mgm.common.model.Event>::class.java).toList()
                cachedEvents = events
                callback.onEventsLoaded(events)
            }

            override fun onDataNotAvailable() {
                val localData = localDataSource.getLocalData()
                val events = gson.fromJson(localData , Array<uk.co.cerihughes.mgm.common.model.Event>::class.java).toList()
                callback.onEventsLoaded(events)
            }
        })
    }
}