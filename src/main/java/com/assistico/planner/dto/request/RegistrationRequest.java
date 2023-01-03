package com.assistico.planner.dto.request;

import com.assistico.planner.validations.annotation.PasswordValueMatch;
import com.assistico.planner.validations.annotation.UniqueEmail;
import com.assistico.planner.validations.annotation.UniqueUsername;
import com.assistico.planner.validations.annotation.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;



@Data
@AllArgsConstructor
@NoArgsConstructor
@PasswordValueMatch.List(
        @PasswordValueMatch(
                field = "password",
                fieldMatch = "confirmPassword",
                message = "passwords does not match"
        )
)
@Builder
public class RegistrationRequest {

    @Schema(description = "The email address of the user. Must be unique and a valid email address.", example = "zilvinas.vysniauskas0147@gmail.com")
    @UniqueEmail
    @Email
    @NotBlank
    @Length(min = 5, max = 41)
    private String email;

    @Schema(description = "The username of the user. Must be unique and between 3 and 20 characters long.", example = "john.doe")
    @NotBlank
    @Length(min = 3, max = 20)
    @UniqueUsername
    private String username;

    @Schema(description = "The password of the user. Must meet certain criteria (e.g. at least 8 characters long, contains a number, special character, and capital letter etc.).", example = "Password123?")
    @ValidPassword
    @NotBlank
    private String password;

    @Schema(description = "The password confirmation of the user. Must match the password.", example = "Password123?")
    @ValidPassword
    @NotBlank
    private String confirmPassword;
}
