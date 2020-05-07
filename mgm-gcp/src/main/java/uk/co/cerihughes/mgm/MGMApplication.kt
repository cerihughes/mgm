package uk.co.cerihughes.mgm

import io.swagger.jaxrs.config.BeanConfig
import io.swagger.jaxrs.listing.ApiListingResource
import io.swagger.jaxrs.listing.SwaggerSerializers
import uk.co.cerihughes.mgm.resource.ContextProvider
import uk.co.cerihughes.mgm.resource.EventResource
import java.util.*
import javax.ws.rs.core.Application

class MGMApplication : Application() {
    override fun getClasses(): Set<Class<*>> {
        val set = HashSet<Class<*>>()
        set.add(ContextProvider::class.java)
        set.add(EventResource::class.java)
        set.add(ApiListingResource::class.java)
        set.add(SwaggerSerializers::class.java)
        return set
    }

    init {
        val beanConfig = BeanConfig()
        beanConfig.version = "1.0.0"
        beanConfig.schemes = arrayOf("https")
        beanConfig.host = "mgm-gcp.appspot.com"
        beanConfig.title = "Music Geek Monthly API"
        beanConfig.basePath = "/"
        beanConfig.resourcePackage = "uk.co.cerihughes.mgm.resource"
        beanConfig.scan = true
    }
}