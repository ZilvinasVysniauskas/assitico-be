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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Tag(name = "Authentication", description = "API for authentication and user registration")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    public AuthenticationResponse register(@Valid @RequestBody @Schema(implementation = RegistrationRequest.class) RegistrationRequest registrationRequest) throws ConfirmationEmailNotSentException {
        return authService.register(registrationRequest);
    }

    @GetMapping("/accountVerification/{token}")
    @Operation(summary = "Activate a user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully activated the user account", content = @Content(schema = @Schema(type = "string", example = "user was enabled"))),
            @ApiResponse(responseCode = "404", description = "User not found by email token")
    })
    public String activateAccount(@PathVariable String token) throws UserNotFoundByEmailToken {
        return authService.activate(token);
    }



    @PostMapping("/login")
    @Operation(summary = "Log in a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully logged in the user", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    public AuthenticationResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh/token")
    @Operation(summary = "Refresh a user's access token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully refreshed the access token", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid refresh token")
    })
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) throws InvalidRefreshTokenException {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    @Operation(summary = "Log out a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully logged out the user", content = @Content(schema = @Schema(type = "string", example = "Refresh Token Deleted Successfully!!"))),
            @ApiResponse(responseCode = "400", description = "Invalid refresh token")
    })
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(OK).body("Refresh Token Deleted Successfully!!");
    }

}
