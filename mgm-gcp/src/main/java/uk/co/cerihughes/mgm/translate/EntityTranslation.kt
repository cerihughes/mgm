package uk.co.cerihughes.mgm.translate

import uk.co.cerihughes.mgm.model.interim.InterimEvent

interface EntityTranslation {
    fun preprocess(interimEvents: List<InterimEvent>)
}