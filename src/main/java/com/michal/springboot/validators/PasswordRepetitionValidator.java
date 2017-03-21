package com.michal.springboot.validators;

import com.michal.springboot.domain.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;





public class PasswordRepetitionValidator implements ConstraintValidator<PasswordRepetition, User> {

    public void initialize(PasswordRepetition constraintAnnotation) {
    }

    public boolean isValid(User user, ConstraintValidatorContext context) {
    	
    	if(user.getPassword().equals(user.getPassword1()))
    	{
    		return true;
    	}
    	else 
    	{
    		return false;
    	}
    }

	

}
