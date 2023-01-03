package com.assistico.planner.dto.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {

    @Schema(description = "A JWT token that can be used to authenticate the user.", required = true, example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
    private String authenticationToken;

    @Schema(description = "The username of the authenticated user.", required = true, example = "john.doe")
    private String username;

    @Schema(description = "The date and time when the authentication token expires.", required = true, example = "2022-01-01T12:00:00Z")
    private Instant expiresAt;

    @Schema(description = "A refresh token that can be used to obtain a new authentication token.", required = true, example = "1a2b3c4d5e6f7g8h9i0j")
    private String refreshToken;


}
