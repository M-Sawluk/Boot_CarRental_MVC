package com.michal.springboot.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.michal.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;


public class UserNameValidator implements ConstraintValidator<UserName, String> {

    private UserRepository userRepository;

    public UserNameValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(UserName constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (userRepository.findByUsername(value).getUsername().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }


}
