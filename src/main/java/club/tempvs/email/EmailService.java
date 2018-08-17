package club.tempvs.email;

import club.tempvs.email.model.EmailPayload;
import club.tempvs.rest.auth.AuthenticationException;
import club.tempvs.rest.auth.TokenHelper;
import com.sendgrid.*;

import java.io.IOException;

public class EmailService {

    private static final String FROM = "no_reply@tempvs.club";
    private static final String CONTENT_TYPE = "text/html";
    private static final String SENDGRID_API_KEY = System.getenv("SENDGRID_API_KEY");

    private TokenHelper tokenHelper;

    public EmailService(TokenHelper tokenHelper) {
        this.tokenHelper = tokenHelper;
    }

    public void doSend(EmailPayload payload, String token) throws IOException {
        tokenHelper.authenticate(token);
        payload.validate();
        sendEmail(payload);
    }

    private void sendEmail(EmailPayload payload) throws IOException {
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
