package com.assistico.planner.service;

import com.assistico.planner.service.MailContentBuilder;
import com.assistico.planner.utils.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.SessionEvent;
import org.springframework.mail.MailException;

import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.*;
import java.io.IOException;

import java.net.URL;
import java.util.Properties;

@Service
@AllArgsConstructor
@Slf4j
public
class MailService {

//    private final JavaMailSender mailSender;
//    private final MailContentBuilder mailContentBuilder;


    @Async
    public void sendMail() throws Exception {

        // Sender's email ID needs to be mentioned
        String from = "arijuskipras@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("zilvinas.vysniauskas0147@gmail.com", "tuteosinanqrwlmu");

            }

        });
        int count = 0;
        while (true){

            try {
//            mailSender.send(messagePreparator);
//            log.info("Activation email sent!!");
                MimeMessage message = new MimeMessage(session);

                // Set From: header field of the header.
                message.setFrom(new InternetAddress(from));

                // Set To: header field of the header.
                message.addRecipient(Message.RecipientType.TO, new InternetAddress("zalavakas0147@gmail.com"));

                // Set Subject: header field
                message.setSubject("Elon Musk");

                // Now set the actual message
                message.setText("Asile kurva reikėjo varyt gert su manim, danar turiu šūdais kažkokias užsiminėt " + count++ );


                System.out.println("sending...");
                // Send message
                Transport.send(message);

            } catch (MailException e) {
                log.error("Exception occurred when sending mail", e);
                throw new Exception("implement this message");
            }
        }

    }

}
