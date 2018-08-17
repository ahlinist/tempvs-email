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

    private final static String TOKEN_HASH = "tokenHash";

    private EmailService emailService;

    @Mock
    private EmailPayload payload;
    @Mock
    private TokenHelper tokenHelper;

    @Before
    public void setup() {
        emailService = new EmailService(tokenHelper);
    }

    @Test(expected = AuthenticationException.class)
    public void testDoSendWithoutToken() throws Exception {
        doThrow(new AuthenticationException()).when(tokenHelper).authenticate(null);

        emailService.doSend(payload, null);

        verify(tokenHelper).authenticate(null);
        verifyNoMoreInteractions(tokenHelper);
    }

    @Test(expected = AuthenticationException.class)
    public void testDoSendWithInvalidToken() throws Exception {
        doThrow(new AuthenticationException()).when(tokenHelper).authenticate("invalidToken");
        emailService.doSend(payload, "invalidToken");

        verify(tokenHelper).authenticate("invalidToken");
        verifyNoMoreInteractions(tokenHelper);
    }

    @Test(expected = PayloadMalformedException.class)
    public void testDoSendWithMalformedPayload() throws Exception {
        doThrow(new PayloadMalformedException("Error message")).when(payload).validate();

        emailService.doSend(payload, TOKEN_HASH);

        verify(tokenHelper).authenticate(TOKEN_HASH);
        verify(payload).validate();
        verifyNoMoreInteractions(payload);
        verifyNoMoreInteractions(tokenHelper);
    }
}
