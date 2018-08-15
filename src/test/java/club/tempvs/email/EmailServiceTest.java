package club.tempvs.email;

import club.tempvs.email.model.EmailPayload;
import club.tempvs.rest.auth.AuthenticationException;
import club.tempvs.rest.auth.TokenHelper;
import club.tempvs.rest.model.PayloadMalformedException;
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
    private EmailPayload payload;
    @Mock
    private TokenHelper tokenHelper;

    @Before
    public void setup() {
        when(tokenHelper.getTokenHash()).thenReturn(TOKEN_HASH);
        emailService = new EmailService(tokenHelper);
    }

    @Test(expected = AuthenticationException.class)
    public void testDoSendWithoutToken() throws Exception {
        emailService.doSend(payload, null);
    }

    @Test(expected = AuthenticationException.class)
    public void testDoSendWithInvalidToken() throws Exception {
        emailService.doSend(payload, "invalidToken");
    }

    @Test(expected = PayloadMalformedException.class)
    public void testDoSendWithMalformedPayload() throws Exception {
        doThrow(new PayloadMalformedException("Error message")).when(payload).validate();

        emailService.doSend(payload, TOKEN_HASH);

        verify(payload).validate();
        verifyNoMoreInteractions(payload);
    }
}
