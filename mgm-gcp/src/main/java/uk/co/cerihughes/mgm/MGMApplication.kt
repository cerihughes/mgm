package uk.co.cerihughes.mgm

import uk.co.cerihughes.mgm.resource.ContextProvider
import uk.co.cerihughes.mgm.resource.EventResource
import java.util.*
import javax.ws.rs.core.Application

class MGMApplication : Application() {
    override fun getClasses(): Set<Class<*>> {
        val set = HashSet<Class<*>>()
        set.add(ContextProvider::class.java)
        set.add(EventResource::class.java)
        return set
    }
}