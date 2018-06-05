package club.tempvs.email;

import javax.mail.Session;

public class EmailServiceFactory {

    private static EmailService emailService;

    public static EmailService getInstance() {
        if (emailService == null) {
            String tokenHash = EmailParameterHelper.getTokenHash();
            String userName = EmailParameterHelper.getSmtpUserName();
            Session emailSession = EmailParameterHelper.getEmailSession();
            emailService = new EmailService(tokenHash, userName, emailSession);
            return emailService;
        } else {
            return emailService;
        }
    }
}
