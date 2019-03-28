package uk.co.cerihughes.mgm.common.repository

interface Repository {

    interface GetEventsCallback {
        fun onEventsLoaded(data: List<uk.co.cerihughes.mgm.common.model.Event>)
    }

    fun getEvents(callback: GetEventsCallback)
}