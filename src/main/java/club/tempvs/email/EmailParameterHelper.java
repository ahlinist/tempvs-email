package club.tempvs.email;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Properties;
import javax.mail.Authenticator;

public class EmailParameterHelper {

    private static final String TOKEN = System.getenv("TOKEN");
    private static final String SMTP_HOST = System.getenv("SMTP_HOST");
    private static final String SMTP_PORT = System.getenv("SMTP_PORT");
    private static final String SMTP_USERNAME = System.getenv("SMTP_USERNAME");
    private static final String SMTP_PASSWORD = System.getenv("SMTP_PASSWORD");

    public String getSmtpUserName() {
        return SMTP_USERNAME;
    }

    public String getTokenHash() {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] tokenBytes = TOKEN.getBytes("UTF-8");
            byte[] digest = messageDigest.digest(tokenBytes);
            BigInteger number = new BigInteger(1, digest);
            return number.toString(16);
        } catch (Exception e) {
            return null;
        }
    }

    public Session getEmailSession() {
        Session session = Session.getDefaultInstance(getEmailProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
            }
        });

        return session;
    }

    private Properties getEmailProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.socketFactory.port", SMTP_PORT);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", SMTP_PORT);
        return properties;
    }
}