package uk.co.cerihughes.mgm.common.viewmodel

import android.arch.lifecycle.ViewModel
import android.os.Handler
import android.os.Looper

abstract class RemoteDataLoadingViewModel(private val repository: uk.co.cerihughes.mgm.common.repository.Repository): ViewModel() {

    private val backgroundThreadHandler = Handler()
    private val mainThreadHandler = Handler(Looper.getMainLooper())

    interface LoadDataCallback {
        fun onDataLoaded()
    }

    fun loadData(callback: LoadDataCallback) {
        backgroundThreadHandler.post {
            repository.getEvents(object: uk.co.cerihughes.mgm.common.repository.Repository.GetEventsCallback {
                override fun onEventsLoaded(data: List<uk.co.cerihughes.mgm.common.model.Event>) {
                    mainThreadHandler.post {
                        setEvents(data)
                        callback.onDataLoaded()
                    }
                }
            })
        }
    }

    abstract fun setEvents(events: List<uk.co.cerihughes.mgm.common.model.Event>)
}