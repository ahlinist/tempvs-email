package club.tempvs.email;

import club.tempvs.email.auth.AuthenticationException;
import club.tempvs.email.json.Payload;
import club.tempvs.email.json.PayloadMalformedException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

public class EmailService {

    private String tokenHash;
    private String smtpUsername;
    private Session emailSession;

    public EmailService(String tokenHash, String smtpUsername, Session emailSession) {
        this.tokenHash = tokenHash;
        this.smtpUsername = smtpUsername;
        this.emailSession = emailSession;
    }

    public void doSend(Payload payload, String token) throws MessagingException {
        authenticate(token);
        validate(payload);
        sendEmail(payload);
    }

    private void authenticate(String receivedToken) {
        if (receivedToken == null || !receivedToken.equals(tokenHash)) {
            throw new AuthenticationException();
        }
    }

    private void validate(Payload payload) {
        if (payload == null) {
            throw new PayloadMalformedException("Payload is empty");
        }

        Boolean payloadValid = Boolean.TRUE;
        List<String> errors = new ArrayList<>();

        if (payload.getEmail() == null) {
            payloadValid = Boolean.FALSE;
            errors.add("Email address is empty");
        }

        if (payload.getSubject() == null) {
            payloadValid = Boolean.FALSE;
            errors.add("Subject is empty");
        }

        if (payload.getBody() == null) {
            payloadValid = Boolean.FALSE;
            errors.add("Message is empty");
        }

        if (!payloadValid) {
            throw new PayloadMalformedException(String.join(", ", errors));
        }
    }

    private void sendEmail(Payload payload) throws MessagingException {
        Message message = new MimeMessage(emailSession);
        message.setFrom(new InternetAddress(smtpUsername));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(payload.getEmail()));
        message.setSubject(payload.getSubject());
        message.setContent(payload.getBody(), "text/html");
        Transport.send(message);
    }
}
