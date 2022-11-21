package com.assistico.planner.validations.validators;

import com.assistico.planner.service.user.UserService;
import com.assistico.planner.validations.annotation.UniqueUsername;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final UserService userService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !userService.isUserNameExisting(value);
    }
}
