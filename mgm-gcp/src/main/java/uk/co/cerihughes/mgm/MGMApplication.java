package uk.co.cerihughes.mgm;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import uk.co.cerihughes.mgm.resource.ContextProvider;
import uk.co.cerihughes.mgm.resource.EventResource;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class MGMApplication extends Application {

    public MGMApplication() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[] { "http" });
        beanConfig.setHost("mgm-gcp.appspot.com");
        beanConfig.setTitle("Music Geek Monthly API");
        beanConfig.setBasePath("/");
        beanConfig.setResourcePackage("uk.co.cerihughes.mgm.resource");
        beanConfig.setScan(true);
    }

    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> set = new HashSet<Class<?>>();

        set.add(ContextProvider.class);
        set.add(EventResource.class);

        set.add(ApiListingResource.class);
        set.add(SwaggerSerializers.class);

        return set;
    }
}