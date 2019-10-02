package club.tempvs.email.amqp;

import club.tempvs.email.model.EmailPayload;
import club.tempvs.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@RequiredArgsConstructor
@EnableBinding(EmailEventProcessor.class)
public class EmailEventHandler {

    private final EmailService emailService;

    @StreamListener(EmailEventProcessor.SEND_EMAIL)
    public void sendEmail(EmailPayload payload) {
        String email = payload.getEmail();
        String subject = payload.getSubject();
        String body = payload.getBody();
        emailService.send(email, subject, body);
    }
}
