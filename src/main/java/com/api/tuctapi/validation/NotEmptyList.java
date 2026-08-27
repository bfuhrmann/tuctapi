package com.api.tuctapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotEmptyListValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmptyList {
    String message() default "A lista não pode estar vazia";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
