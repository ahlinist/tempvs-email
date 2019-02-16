package club.tempvs.email.controller;

import club.tempvs.email.model.EmailPayload;
import club.tempvs.email.service.EmailService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class EmailControllerTest {

    private static final String TOKEN = "token";

    private EmailController emailController;

    @Mock
    private EmailService emailService;
    @Mock
    private EmailPayload emailPayload;

    @Before
    public void setup() {
        emailController = new EmailController(emailService);
    }

    @Test
    public void testSend() throws IOException {
        String email = "email@test.com";
        String subject = "subject";
        String body = "<p>email text</p>";

        when(emailPayload.getEmail()).thenReturn(email);
        when(emailPayload.getSubject()).thenReturn(subject);
        when(emailPayload.getBody()).thenReturn(body);

        ResponseEntity result = emailController.send(emailPayload);

        verify(emailPayload).getEmail();
        verify(emailPayload).getSubject();
        verify(emailPayload).getBody();
        verify(emailService).send(email, subject, body);
        verifyNoMoreInteractions(emailPayload, emailService);

        assertEquals("ResponseEntity with OK (200) status is returned", HttpStatus.OK, result.getStatusCode());
    }
}
