package com.assistico.planner.validations.annotation;

import com.assistico.planner.validations.validators.UniqueUsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ElementType.FIELD})
@Constraint(validatedBy = {UniqueUsernameValidator.class})
@Retention(RUNTIME)
public @interface UniqueUsername {

    //todo add username to error message
    String message() default "Usernmae already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        UniqueUsername[] value();
    }
}

