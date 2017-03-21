package com.michal.springboot.validators;

import com.michal.springboot.exception.RecaptchaServiceException;
import com.michal.springboot.forms.ReCaptchaForm;
import com.michal.springboot.service.ReCaptchaService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RecaptchaFormValidator implements Validator {

    private ReCaptchaService recaptchaService;

    public RecaptchaFormValidator(ReCaptchaService recaptchaService) {
        this.recaptchaService = recaptchaService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ReCaptchaForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ReCaptchaForm form = (ReCaptchaForm) target;
        try {
            if (!recaptchaService.isResponseValid(form.getRecaptchaResponse())) {

                errors.reject("Captcha.Invalid");
                errors.rejectValue("recaptchaResponse", "Captcha.Invalid");
            }
        } catch (RecaptchaServiceException e) {
            errors.reject("Captcha.Unavailbe");
        }
    }


}