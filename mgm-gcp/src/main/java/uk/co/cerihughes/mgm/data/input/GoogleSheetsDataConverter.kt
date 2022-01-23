package uk.co.cerihughes.mgm.data.input

import uk.co.cerihughes.mgm.model.interim.InterimEvent

interface GoogleSheetsDataConverter {
    fun convert(data: List<List<String>>): List<InterimEvent>?
}
