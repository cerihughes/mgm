package uk.co.cerihughes.mgm.common.repository.local

interface LocalDataSource {
    fun getLocalData(): String?
    fun persistRemoteData(remoteData: String): Boolean
}