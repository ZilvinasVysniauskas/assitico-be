package com.assistico.planner.controller;

import com.assistico.planner.dto.api.AuthenticationResponse;
import com.assistico.planner.dto.request.LoginRequest;
import com.assistico.planner.dto.request.RegistrationRequest;
import com.assistico.planner.exceptions.ConfirmationEmailNotSentException;
import com.assistico.planner.exceptions.UserNotFoundByEmailToken;
import com.assistico.planner.service.login.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegistrationRequest registrationRequest) throws ConfirmationEmailNotSentException {
        return authService.register(registrationRequest);
    }

    @GetMapping("/accountVerification/{token}")
    public String activateAccount(@PathVariable String token) throws UserNotFoundByEmailToken {
        return authService.activate(token);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

}
