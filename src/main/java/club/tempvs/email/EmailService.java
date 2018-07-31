package club.tempvs.email;

import club.tempvs.email.auth.AuthenticationException;
import club.tempvs.email.auth.TokenHelper;
import club.tempvs.email.json.Payload;
import club.tempvs.email.json.PayloadMalformedException;
import com.sendgrid.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmailService {

    private static final String FROM = "no_reply@tempvs.club";
    private static final String CONTENT_TYPE = "text/html";
    private static final String SENDGRID_API_KEY = System.getenv("SENDGRID_API_KEY");

    private String tokenHash;

    public EmailService(TokenHelper tokenHelper) {
        this.tokenHash = tokenHelper.getTokenHash();
    }

    public void doSend(Payload payload, String token) throws IOException {
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

    private void sendEmail(Payload payload) throws IOException {
        Email from = new Email(FROM);
        String subject = payload.getSubject();
        Email to = new Email(payload.getEmail());
        Content content = new Content(CONTENT_TYPE, payload.getBody());
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(SENDGRID_API_KEY);
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        sg.api(request);
    }
}
