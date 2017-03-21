package com.michal.springboot.validators;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;



@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = UserEmailValidator.class)
@Documented

public @interface UserEmail
{
	String message() default "{validation.user.emialDB}";
	
	Class<?>[] groups() default {};
	
	public abstract Class<? extends Payload>[] payload() default {};
}
