package club.tempvs.email.amqp;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface EmailEventProcessor {

    String SEND_EMAIL = "email.send";

    @Input(SEND_EMAIL)
    SubscribableChannel sendEmail();
}
