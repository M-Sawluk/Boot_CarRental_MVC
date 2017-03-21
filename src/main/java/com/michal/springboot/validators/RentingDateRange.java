package com.michal.springboot.validators;


import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = RentingDateRangeValidator.class)
@Documented
public @interface RentingDateRange {

    String message() default "Correct your renting date range";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
