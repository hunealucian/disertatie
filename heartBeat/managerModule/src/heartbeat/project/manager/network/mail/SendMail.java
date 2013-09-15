package project.manager.network.mail;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * User: Hunea Paul Lucian
 * Date: 9/14/13
 */
public class SendMail {

    public static String sendMail(String mailfrom, String subject, String message, String mailto) {
        String strResult = "";
        System.out.println("\tMailing Process Started ............\t");
        try {
            Properties props = new Properties();
            props.put("mail.smtp.user", "hunealucian@gmail.com");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");
            //props.put("mail.smtp.debug", "true");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.fallback", "false");

            try {
                Authenticator auth = new SMTPAuthenticator();
                Session session = Session.getInstance(props, auth);
                MimeMessage msg = new MimeMessage(session);
                msg.setText(message);
                msg.setSubject(subject);
                msg.setFrom(new InternetAddress(mailfrom));
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mailto));
                Transport.send(msg);
            } catch (MessagingException e) {
                e.printStackTrace();
            }

        } catch (Exception exception) {
            System.out.println("\tInitialization Block........." + exception.getMessage());
        }
        System.out.println("\tMailing Process Ended ............\t");
        return strResult;
    }

    private static class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("hunealucian@gmail.com", "hcaz88inexistent!!");
        }
    }
}
