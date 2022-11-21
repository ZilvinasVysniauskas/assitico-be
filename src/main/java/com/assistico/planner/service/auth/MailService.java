package com.assistico.planner.service.auth;

import com.assistico.planner.utils.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
@Slf4j
public
class MailService {

    private final MailMessageBuilder mailMessageBuilder;
    @Async
    public void sendMail(String mail, String token) throws MessagingException {
        NotificationEmail notificationEmail = NotificationEmail.builder()
                .body("Click to verify email address, http://localhost:8888/api/auth/accountVerification/"
                        + token)
                .subject("subject")
                .recipient(mail)
                .build();
        MimeMessage mailMessage = mailMessageBuilder.getMailMessage(notificationEmail);
        Transport.send(mailMessage);
    }
}
