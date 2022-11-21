package com.assistico.planner.service.auth;

import com.assistico.planner.dto.api.AuthenticationResponse;
import com.assistico.planner.dto.request.LoginRequest;
import com.assistico.planner.dto.request.RefreshTokenRequest;
import com.assistico.planner.dto.request.RegistrationRequest;
import com.assistico.planner.exceptions.ConfirmationEmailNotSentException;
import com.assistico.planner.exceptions.InvalidRefreshTokenException;
import com.assistico.planner.exceptions.UserNotFoundByEmailToken;
import com.assistico.planner.mappers.AuthenticationMapper;
import com.assistico.planner.model.User;
import com.assistico.planner.model.VerificationToken;
import com.assistico.planner.repository.user.UserRepository;
import com.assistico.planner.repository.auth.VerificationTokenRepository;
import com.assistico.planner.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final MailService mailService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    AuthenticationMapper authenticationMapper;


    @Transactional
    public AuthenticationResponse register(RegistrationRequest registrationRequest) throws ConfirmationEmailNotSentException {
        User user = authenticationMapper.registrationRequestToUser(registrationRequest);
        //todo add logic to resend email confirmation
        try {
            VerificationToken verificationToken = generateVerificationToken(user);
            verificationTokenRepository.save(verificationToken);
            userRepository.save(user);
            mailService.sendMail(registrationRequest.getEmail(), verificationToken.getToken());
            return authenticationMapper.registrationRequestToAuthenticationResponse(registrationRequest);
        } catch (MessagingException e) {
            throw new ConfirmationEmailNotSentException("Registration failed " + e.getMessage());
        }

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
        Authentication authenticate = getAuthentication(loginRequest.getUsername(), loginRequest.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return authenticationMapper.loginRequestToAuthenticationResponse(loginRequest);
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws InvalidRefreshTokenException {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        return authenticationMapper.refreshTokenToAuthenticationResponse(refreshTokenRequest);
    }

    private Authentication getAuthentication(String username, String password) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                ));
    }

    private VerificationToken generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        return VerificationToken.builder()
                .token(token)
                .expiryDate(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .user(user).build();
    }
}
