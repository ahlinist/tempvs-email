package club.tempvs.email.api;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import club.tempvs.email.json.Payload;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

public class ApiTest extends JerseyTest {

    @Mock
    private Payload payload;

    @Override
    protected Application configure() {
        return new ResourceConfig(Api.class);
    }

    @Test
    public void testPing() {
        String responseMsg = target().path("api/ping").request().get(String.class);

        assertEquals("pong!", responseMsg);
    }

    @Test
    public void testSend() {
        Response response = target().path("api/send").request().post(Entity.json(payload));

        assertTrue(response instanceof Response);
    }
}
