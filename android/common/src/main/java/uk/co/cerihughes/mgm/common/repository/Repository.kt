package uk.co.cerihughes.mgm.common.repository

import uk.co.cerihughes.mgm.common.model.Event

interface Repository {

    interface GetEventsCallback {
        fun onEventsLoaded(data: List<Event>)
    }

    fun getEvents(callback: GetEventsCallback)
}