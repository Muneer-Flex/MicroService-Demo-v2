package com.bank.governingstate.annotations;

import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogMethodTimeExecution {

    boolean enabled() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
