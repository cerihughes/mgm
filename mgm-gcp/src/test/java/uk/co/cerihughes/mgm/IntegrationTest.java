package uk.co.cerihughes.mgm;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.test.TestPortProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {

    private NettyJaxrsServer netty;
    private int port;

    @BeforeEach
    void startServer() {
        port = TestPortProvider.getPort();
        netty = new NettyJaxrsServer();

        netty.getDeployment().setApplication(new MGMApplication());
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

    @Test
    public void swaggerTest() {
        ResteasyClient client = new ResteasyClientBuilderImpl().build();
        Response response = client.target("http://localhost:" + port + "/swagger.json").request().get();
        MultivaluedMap<String, String> stringHeaders = response.getStringHeaders();
        String contentTypeHeader = stringHeaders.getFirst("Content-Type");
        String swagger = response.readEntity(String.class);

        assertEquals("application/json", contentTypeHeader);
        assertTrue(swagger.contains("AlbumApiModel"));
        assertTrue(swagger.contains("EventApiModel"));
        assertTrue(swagger.contains("ImageApiModel"));
        assertTrue(swagger.contains("LocationApiModel"));
        assertTrue(swagger.contains("PlaylistApiModel"));
        assertTrue(swagger.contains("AlbumTypeApiModel"));
    }
}

