package club.tempvs.email.service.impl;

import club.tempvs.email.service.EmailService;
import club.tempvs.email.util.ObjectFactory;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sendgrid.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private static final String EMAIL_FROM = "no_reply@tempvs.club";
    private static final String TEXT_CONTENT_TYPE = "text/html";

    private final SendGrid sendGrid;
    private final ObjectFactory objectFactory;

    @SneakyThrows
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")
    })
    public void send(String email, String subject, String body) {
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(subject) || StringUtils.isEmpty(body)) {
            throw new IllegalArgumentException("One of the following parameters was empty: email-to ("
                    + email + "), subject (" +  subject + "), body: (" + body + ").");
        }

        Email from = objectFactory.getInstance(Email.class, EMAIL_FROM);
        Email to = objectFactory.getInstance(Email.class, email);
        Content content = objectFactory.getInstance(Content.class, TEXT_CONTENT_TYPE, body);
        Mail mail = objectFactory.getInstance(Mail.class, from, subject, to, content);
        Request request = objectFactory.getInstance(Request.class);

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        sendGrid.api(request);
    }
}
