package club.tempvs.email;

import club.tempvs.email.auth.TokenHelper;

public class EmailServiceFactory {

    private static EmailService emailService;

    public static EmailService getInstance() {
        if (emailService == null) {
            emailService = new EmailService(new TokenHelper());
        }

        return emailService;
    }
}
