package com.assistico.planner.service.login;

import com.assistico.planner.dto.api.AuthenticationResponse;
import com.assistico.planner.dto.mappers.UserMapper;
import com.assistico.planner.dto.request.LoginRequest;
import com.assistico.planner.dto.request.RegistrationRequest;
import com.assistico.planner.exceptions.ConfirmationEmailNotSentException;
import com.assistico.planner.exceptions.UserNotFoundByEmailToken;
import com.assistico.planner.model.User;
import com.assistico.planner.model.VerificationToken;
import com.assistico.planner.repository.UserRepository;
import com.assistico.planner.repository.VerificationTokenRepository;
import com.assistico.planner.security.JwtProvider;
import com.assistico.planner.utils.NotificationEmail;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MailService mailService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Transactional
    public String register(RegistrationRequest registrationRequest) throws ConfirmationEmailNotSentException {
        User user = UserMapper.registrationRequestToUser(registrationRequest, passwordEncoder);
        //todo add logic to resend email confirmation
        try {
            String token = generateVerificationToken(user);
            userRepository.save(user);
            mailService.sendMail(
                    NotificationEmail.builder()
                            .body("Click to verify email address, http://localhost:8888/api/auth/accountVerification/"
                                    + token)
                            .subject("subject")
                            .recipient(user.getEmail())
                            .build());
            return "Registration successful";
        } catch (MessagingException e) {
            throw new ConfirmationEmailNotSentException("Registration failed " + e.getMessage());
        }

    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .user(user).build();
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public String activate(String emailToken) throws UserNotFoundByEmailToken {
        Optional<VerificationToken> optionalVerificationToken = verificationTokenRepository.findByToken(emailToken);
        VerificationToken verificationToken = optionalVerificationToken.orElseThrow(() -> new UserNotFoundByEmailToken("no user by email token"));
        return enableUser(verificationToken);
    }

    private String enableUser(VerificationToken verificationToken) {
        User userToEnable = verificationToken.getUser();
        userToEnable.setEnabled(true);
        userRepository.save(verificationToken.getUser());
        return "user was enabled";
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return AuthenticationResponse.builder()
                .authenticationToken(jwtProvider.generateToken(authenticate))
                .username(loginRequest.getUsername())
                .build();
    }
}
