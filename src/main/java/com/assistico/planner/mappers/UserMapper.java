package com.assistico.planner.mappers;

import com.assistico.planner.dto.request.RegistrationRequest;
import com.assistico.planner.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;

public class UserMapper {

    public static User registrationRequestToUser(RegistrationRequest registrationRequest, PasswordEncoder passwordEncoder) {
        return User.builder()
                .enabled(false)
                .username(registrationRequest.getUsername())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .createdAt(Instant.now())
                .build();
    }

}
