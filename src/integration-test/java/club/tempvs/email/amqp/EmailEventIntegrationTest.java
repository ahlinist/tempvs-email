package club.tempvs.email.amqp;

import club.tempvs.email.model.EmailPayload;
import com.sendgrid.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class EmailEventIntegrationTest {

    @Autowired
    private EmailEventHandler emailEventHandler;

    @MockBean
    private SendGrid sendGrid;

    @Test
    public void testSendEmail() throws Exception {
        String email = "test@test.com";
        String subject = "subject";
        String body = "body";
        EmailPayload payload = new EmailPayload(email, subject, body);
        Email from = new Email("no_reply@tempvs.club");
        Email to = new Email(email);
        Content content = new Content("text/html", body);
        Mail mail = new Mail(from, subject, to, content);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        emailEventHandler.sendEmail(payload);

        verify(sendGrid).api(argThat(new MessageMatcher(request)));
        verifyNoMoreInteractions(sendGrid);
    }

    public class MessageMatcher implements ArgumentMatcher<Request> {

        private Request left;

        public MessageMatcher(Request left) {
            this.left = left;
        }

        @Override
        public boolean matches(Request right) {
            return left.getMethod().equals(right.getMethod()) &&
                    left.getEndpoint().equals(right.getEndpoint()) &&
                    left.getBody().equals(right.getBody());
        }
    }
}
