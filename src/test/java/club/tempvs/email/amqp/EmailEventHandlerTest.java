package club.tempvs.email.amqp;

import club.tempvs.email.model.EmailPayload;
import club.tempvs.email.service.EmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmailEventHandlerTest {

    @InjectMocks
    private EmailEventHandler emailEventHandler;

    @Mock
    private EmailService emailService;
    @Mock
    private EmailPayload emailPayload;

    @Test
    public void testSend() {
        String email = "email@test.com";
        String subject = "subject";
        String body = "<p>email text</p>";

        when(emailPayload.getEmail()).thenReturn(email);
        when(emailPayload.getSubject()).thenReturn(subject);
        when(emailPayload.getBody()).thenReturn(body);

        emailEventHandler.sendEmail(emailPayload);

        verify(emailPayload).getEmail();
        verify(emailPayload).getSubject();
        verify(emailPayload).getBody();
        verify(emailService).send(email, subject, body);
        verifyNoMoreInteractions(emailPayload, emailService);
    }
}
