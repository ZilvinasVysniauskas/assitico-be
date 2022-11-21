package com.assistico.planner.validations.annotation;

import com.assistico.planner.validations.validators.UniqueEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ElementType.FIELD})
@Constraint(validatedBy = {UniqueEmailValidator.class})
@Retention(RUNTIME)
public @interface UniqueEmail {

    String message() default "{UniqueKey.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        UniqueEmail[] value();
    }
}

