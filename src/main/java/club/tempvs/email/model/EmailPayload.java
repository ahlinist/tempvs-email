package club.tempvs.email.model;

import club.tempvs.rest.model.Payload;
import club.tempvs.rest.model.PayloadMalformedException;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.HashSet;
import java.util.Set;

public class EmailPayload implements Payload {

    private String email;
    private String subject;
    private String body;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void validate() {
        boolean payloadValid = true;
        Set<String> errors = new HashSet<>();

        if (email == null) {
            errors.add("Payload doesn't contain 'to' email address.");
        }

        if (!EmailValidator.getInstance().isValid(email)) {
            errors.add("Payload contains invalid 'to' email address.");
        }

        if (subject == null) {
            errors.add("Payload doesn't contain subject.");
        }

        if (body == null) {
            errors.add("Payload doesn't contain body");
        }

        if (!payloadValid) {
            throw new PayloadMalformedException(String.join(", ", errors));
        }
    }
}
