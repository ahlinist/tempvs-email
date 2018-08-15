package club.tempvs.email;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import club.tempvs.email.model.EmailPayload;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

public class EmailApiTest extends JerseyTest {

    @Mock
    private EmailPayload payload;

    @Override
    protected Application configure() {
        return new ResourceConfig(EmailApi.class);
    }

    @Test
    public void testSend() {
        Response response = target().path("api/send").request().post(Entity.json(payload));

        assertTrue(response instanceof Response);
    }
}
