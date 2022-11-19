package com.assistico.planner.service.login;

import com.assistico.planner.utils.NotificationEmail;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@AllArgsConstructor
@Component
public class MailMessageBuilder {
    private final MailContentBuilder mailContentBuilder;
    //todo hide password of email
    private final String emailAddress = "zilvinas.vysniauskas0147@gmail.com";
    private final String emailPassword = "tuteosinanqrwlmu";

    public MimeMessage getMailMessage(NotificationEmail notificationEmail) throws MessagingException {

        //todo put properties in application.properties
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(System.getProperties(), new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailAddress, emailPassword);
            }
        });
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(emailAddress));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(notificationEmail.getRecipient()));
        message.setSubject(notificationEmail.getSubject());
        message.setContent(mailContentBuilder.build(notificationEmail.getBody()), "text/html; charset=utf-8");
        return message;
    }

}
