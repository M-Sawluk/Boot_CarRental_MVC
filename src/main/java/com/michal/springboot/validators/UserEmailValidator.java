package com.michal.springboot.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.michal.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;


public class UserEmailValidator implements ConstraintValidator<UserEmail, String> {

    private UserRepository userRepository;

    public UserEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void initialize(UserEmail constraintAnnotation) {
    }


    public boolean isValid(String email, ConstraintValidatorContext context) {

        if (userRepository.findByEmail(email).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

}
