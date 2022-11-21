package com.assistico.planner.controller;

import com.assistico.planner.dto.api.AuthenticationResponse;
import com.assistico.planner.dto.request.LoginRequest;
import com.assistico.planner.dto.request.RefreshTokenRequest;
import com.assistico.planner.dto.request.RegistrationRequest;
import com.assistico.planner.exceptions.ConfirmationEmailNotSentException;
import com.assistico.planner.exceptions.InvalidRefreshTokenException;
import com.assistico.planner.exceptions.UserNotFoundByEmailToken;
import com.assistico.planner.service.auth.AuthService;
import com.assistico.planner.service.auth.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public AuthenticationResponse register(@Valid @RequestBody RegistrationRequest registrationRequest) throws ConfirmationEmailNotSentException {
        return authService.register(registrationRequest);
    }

    @GetMapping("/accountVerification/{token}")
    public String activateAccount(@PathVariable String token) throws UserNotFoundByEmailToken {
        return authService.activate(token);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) throws InvalidRefreshTokenException {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(OK).body("Refresh Token Deleted Successfully!!");
    }


}
