package club.tempvs.email.service;

import club.tempvs.email.service.impl.EmailServiceImpl;
import club.tempvs.email.util.ObjectFactory;
import com.sendgrid.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTest {

    private static final String EMAIL_FROM = "no_reply@tempvs.club";
    private static final String TEXT_CONTENT_TYPE = "text/html";

    private EmailService emailService;

    @Mock
    private ObjectFactory objectFactory;

    @Mock
    private Email from;

    @Mock
    private Email to;

    @Mock
    private Content content;

    @Mock
    private Mail mail;

    @Mock
    private SendGrid sendGrid;

    @Mock
    private Request request;

    @Before
    public void setup() {
        emailService = new EmailServiceImpl(sendGrid, objectFactory);
    }

    @Test
    public void testSend() throws IOException {
        String email = "test@email.com";
        String subject = "subject";
        String body = "<p>body</p>";
        String builtEmailBody = "built body";

        when(objectFactory.getInstance(Email.class, EMAIL_FROM)).thenReturn(from);
        when(objectFactory.getInstance(Email.class, email)).thenReturn(to);
        when(objectFactory.getInstance(Content.class, TEXT_CONTENT_TYPE, body)).thenReturn(content);
        when(objectFactory.getInstance(Mail.class, from, subject, to, content)).thenReturn(mail);
        when(objectFactory.getInstance(Request.class)).thenReturn(request);
        when(mail.build()).thenReturn(builtEmailBody);

        emailService.send(email, subject, body);

        verify(objectFactory).getInstance(Email.class, EMAIL_FROM);
        verify(objectFactory).getInstance(Email.class, email);
        verify(objectFactory).getInstance(Content.class, TEXT_CONTENT_TYPE, body);
        verify(objectFactory).getInstance(Mail.class, from, subject, to, content);
        verify(objectFactory).getInstance(Request.class);
        verify(request).setMethod(Method.POST);
        verify(request).setEndpoint("mail/send");
        verify(mail).build();
        verify(request).setBody(builtEmailBody);
        verify(sendGrid).api(request);
        verifyNoMoreInteractions(objectFactory, from, to, content, mail, sendGrid, request);
    }
}
