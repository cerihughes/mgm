package uk.co.cerihughes.mgm;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.test.TestPortProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.cerihughes.mgm.service.ContextProvider;
import uk.co.cerihughes.mgm.service.EventsService;

import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {

    private NettyJaxrsServer netty;
    private int port;

    @BeforeEach
    void startServer() {
        port = TestPortProvider.getPort();
        netty = new NettyJaxrsServer();

        netty.getDeployment().setActualProviderClasses(Collections.singletonList(ContextProvider.class));
        netty.getDeployment().setActualResourceClasses(Collections.singletonList(EventsService.class));
        netty.setPort(port);
        netty.setRootResourcePath("");
        netty.setSecurityDomain(null);
        netty.start();
    }

    @AfterEach
    void stopServer() {
        netty.stop();
    }

    @Test
    public void integrationTest() {
        ResteasyClient client = new ResteasyClientBuilderImpl().build();
        Response response = client.target("http://localhost:" + port + "/mgm.json").request().get();
        EntityTag etag = response.getEntityTag();
        MultivaluedMap<String, String> stringHeaders = response.getStringHeaders();
        String cacheControlHeader = stringHeaders.getFirst("Cache-Control");
        String expiresHeader = stringHeaders.getFirst("Expires");
        String contentTypeHeader = stringHeaders.getFirst("Content-Type");
        String events = response.readEntity(String.class);

        assertNotNull(etag);
        assertNotNull(cacheControlHeader);
        assertNotNull(expiresHeader);
        assertEquals("application/json", contentTypeHeader);
        assertTrue(events.contains("17/02/2011"));
        assertTrue(events.contains("\"new\""));
        assertTrue(events.contains("\"classic\""));
        assertFalse(events.contains("null"));
    }
}

