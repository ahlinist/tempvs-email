package club.tempvs.email.service;

import java.io.IOException;

public interface EmailService {
    void send(String email, String subject, String body) throws IOException;
}
