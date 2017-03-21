package com.michal.springboot.forms;

import com.michal.springboot.validators.UserEmail;
import org.hibernate.validator.constraints.Email;

public class EmailForm {

    @Email(message = "{pattern.user.email.validation}")
    @UserEmail
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Email [email=" + email + "]";
    }
}
