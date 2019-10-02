package club.tempvs.email.service;

public interface EmailService {

    void send(String email, String subject, String body);
}
