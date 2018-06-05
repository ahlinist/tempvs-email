package club.tempvs.email;

import club.tempvs.email.auth.AuthenticationException;
import club.tempvs.email.EmailService;
import club.tempvs.email.json.Payload;
import club.tempvs.email.json.PayloadMalformedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTest {

    private final static String EMAIL_BODY = "body";
    private final static String TOKEN_HASH = "tokenHash";
    private final static String EMAIL_SUBJECT = "subject";
    private final static String SMTP_USERNAME = "user@test.com";

    private EmailService emailService;

    @Mock
    private Payload payload;

    @Before
    public void setup() {
        //TODO: mock Session properly (final class) instead of using null
        emailService = new EmailService(TOKEN_HASH, SMTP_USERNAME, null);
    }

    @Test(expected = AuthenticationException.class)
    public void testDoSendWithoutToken() throws Exception {
        emailService.doSend(payload, null);
    }

    @Test(expected = AuthenticationException.class)
    public void testDoSendNonAuthenticated() throws Exception {
        emailService.doSend(payload, "incorrectToken");
    }

    @Test(expected = PayloadMalformedException.class)
    public void testDoSendWithoutPayload() throws Exception {
        emailService.doSend(null, TOKEN_HASH);
    }

    @Test(expected = PayloadMalformedException.class)
    public void testDoSendWithoutEmail() throws Exception {
        when(payload.getBody()).thenReturn(EMAIL_BODY);
        when(payload.getSubject()).thenReturn(EMAIL_SUBJECT);

        emailService.doSend(payload, TOKEN_HASH);

        verify(payload).getEmail();
        verifyNoMoreInteractions(payload);
    }

    @Test(expected = PayloadMalformedException.class)
    public void testDoSendWithoutSubject() throws Exception {
        when(payload.getEmail()).thenReturn(SMTP_USERNAME);
        when(payload.getBody()).thenReturn(EMAIL_BODY);

        emailService.doSend(payload, TOKEN_HASH);

        verify(payload).getEmail();
        verify(payload).getSubject();
        verifyNoMoreInteractions(payload);
    }

    @Test(expected = PayloadMalformedException.class)
    public void testDoSendWithoutBody() throws Exception {
        when(payload.getEmail()).thenReturn(SMTP_USERNAME);
        when(payload.getSubject()).thenReturn(EMAIL_SUBJECT);

        emailService.doSend(payload, TOKEN_HASH);

        verify(payload).getEmail();
        verify(payload).getSubject();
        verify(payload).getBody();
        verifyNoMoreInteractions(payload);
    }
}
