package com.assistico.planner.controller;

import com.assistico.planner.dto.request.RegistrationRequest;
import com.assistico.planner.service.AuthService;
import com.assistico.planner.service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final MailService mailService;

    @PostMapping("/register")
    public void register(@RequestBody RegistrationRequest registrationRequest) throws Exception {
//        try {
//            authService.register(registrationRequest);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("registration failed");
//        }
//        return ResponseEntity.status(HttpStatus.OK).body("registration successful");
        mailService.sendMail();
    }

}
