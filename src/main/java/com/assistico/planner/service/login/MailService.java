package com.assistico.planner.service.login;

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
    public void sendMail(NotificationEmail notificationEmail) throws MessagingException {
        MimeMessage mailMessage = mailMessageBuilder.getMailMessage(notificationEmail);
        Transport.send(mailMessage);
    }
}
