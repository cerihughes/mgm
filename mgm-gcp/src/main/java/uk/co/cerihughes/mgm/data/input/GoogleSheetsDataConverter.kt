package uk.co.cerihughes.mgm.data.input

import uk.co.cerihughes.mgm.model.interim.InterimEvent

interface GoogleSheetsDataConverter {
    fun convert(json: String): List<InterimEvent>?
}