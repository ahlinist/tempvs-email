package club.tempvs;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SendEmailTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(SendEmail.class);
    }

    /**
     * Test to see that the message "pong!" is sent in the response.
     */
    @Test
    public void testGetIt() {
        final String responseMsg = target().path("ping").request().get(String.class);

        assertEquals("pong!", responseMsg);
    }
}
