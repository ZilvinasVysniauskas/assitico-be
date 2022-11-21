package com.assistico.planner.validations.validators;

import com.assistico.planner.service.user.UserService;
import com.assistico.planner.validations.annotation.UniqueEmail;
import com.assistico.planner.validations.annotation.UniqueUsername;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserService userService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !userService.isEmailExisting(value);
    }
}
