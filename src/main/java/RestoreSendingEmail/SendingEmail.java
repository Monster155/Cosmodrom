package RestoreSendingEmail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class SendingEmail {

    public static SendingEmail here;

    static {
        here = new SendingEmail();
    }

    String to = "damir180501@mail.ru";
    String from = "itlab.dev@mail.ru";
    Properties prop = System.getProperties();

    private SendingEmail() {
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.mail.ru");
        prop.put("mail.smtp.port", "25");
        prop.put("mail.smtp.ssl.trust", "smtp.mail.ru");
    }

    public static void main(String[] args) {
        here.sendMessage("Restore Password", "Vam Ban", "damir180501@mail.ru");
    }

    public void sendMessage(String theme, String text, String sendMailTo) {
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("itlab.dev@mail.ru", "Elogof70");
            }
        });

        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(from));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(sendMailTo));
            message.setSubject(theme);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(text, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (MessagingException e) {
            System.out.println(e.getMessage() + " " + e.getCause());
        }

    }
}
