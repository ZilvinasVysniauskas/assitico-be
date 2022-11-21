package com.assistico.planner.dto.request;

import com.assistico.planner.validations.annotation.PasswordValueMatch;
import com.assistico.planner.validations.annotation.UniqueEmail;
import com.assistico.planner.validations.annotation.UniqueUsername;
import com.assistico.planner.validations.annotation.ValidPassword;
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

    @UniqueEmail
    @Email
    @NotBlank
    @Length(min = 5, max = 41)
    private String email;

    @NotBlank
    @Length(min = 3, max = 20)
    @UniqueUsername
    private String username;

    @ValidPassword
    @NotBlank
    private String password;

    @ValidPassword
    @NotBlank
    private String confirmPassword;
}
