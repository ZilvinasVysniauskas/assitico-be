package com.assistico.planner.service;

import com.assistico.planner.dto.mappers.UserMapper;
import com.assistico.planner.dto.request.RegistrationRequest;
import com.assistico.planner.model.User;
import com.assistico.planner.model.VerificationToken;
import com.assistico.planner.repository.UserRepository;
import com.assistico.planner.repository.VerificationTokenRepository;
import com.assistico.planner.utils.NotificationEmail;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final MailService mailService;
    private final VerificationTokenRepository verificationTokenRepository;

    @Transactional
    public void register(RegistrationRequest registrationRequest) throws Exception {
        User user = UserMapper.registrationRequestToUser(registrationRequest, passwordEncoder);
        userRepository.save(user);

        String token = generateVerificationToken(user);

//        mailService.sendMail(NotificationEmail.builder()
//                        .body("Body, http://localhost:8888/api/auth/accountVerification/" + token)
//                        .subject("subject")
//                        .recipient(user.getEmail())
//                .build());
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .user(user).build();
        verificationTokenRepository.save(verificationToken);
        return token;
    }
}
