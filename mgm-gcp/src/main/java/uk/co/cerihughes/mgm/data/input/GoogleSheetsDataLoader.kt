package uk.co.cerihughes.mgm.data.input

interface GoogleSheetsDataLoader {
    fun loadSheetsData(): List<List<String>>?
}
