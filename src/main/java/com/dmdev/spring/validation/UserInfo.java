package com.dmdev.spring.validation;

import com.dmdev.spring.validation.impl.UserInfoValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = { UserInfoValidator.class})
@Target(TYPE)
@Retention(RUNTIME)
public @interface UserInfo {

    String message() default "Firstname or lastname should be filled in!";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
