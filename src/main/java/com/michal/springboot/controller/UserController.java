package com.michal.springboot.controller;

import com.michal.springboot.domain.User;
import com.michal.springboot.exception.ConfirmationExpiredException;
import com.michal.springboot.exception.ConfirmationFailedException;
import com.michal.springboot.forms.FormUser;
import com.michal.springboot.service.OrderService;
import com.michal.springboot.service.ProductService;
import com.michal.springboot.service.UserService;
import com.michal.springboot.validators.RecaptchaFormValidator;
import com.michal.springboot.forms.EmailForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class UserController {

    private UserService userService;

    private OrderService orderService;

    private ProductService productService;

    private RecaptchaFormValidator recaptchaFormValidator;

    public UserController(UserService userService, OrderService orderService, ProductService productService, RecaptchaFormValidator recaptchaFormValidator) {
        this.userService = userService;
        this.orderService = orderService;
        this.productService = productService;
        this.recaptchaFormValidator = recaptchaFormValidator;
    }

    @ModelAttribute("recaptchaSiteKey")
    public String getRecaptchaSiteKey(@Value("${reCaptcha.site}") String recaptchaSiteKey) {
        return recaptchaSiteKey;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signUp(Model model) {
        User newUser = new User();
        model.addAttribute("newUser", newUser);

        return "registration";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signUpProcess(@ModelAttribute("newUser") @Valid User newUser, BindingResult result, Model model) {

        if (result.hasErrors()) {

            return "registration";
        }

        userService.saveUser(newUser);

        model.addAttribute("name", newUser.getFirstName());

        return "redirect:/registration-preconfirm";
    }

    @RequestMapping("/registration-preconfirm")
    public String registrationPre() {
        return "registration-preconfirm";
    }

    @RequestMapping(value = "/registration-confirm", method = RequestMethod.GET)
    public String confirmReg(@RequestParam("s") Long userId, @RequestParam("d") String digest, Model model) {
        try {
            userService.confirmUser(userId, digest);

            model.addAttribute("name", userService.getUser(userId));

            return "accountSuccess";
        } catch (ConfirmationExpiredException e) {
            model.addAttribute("expired", true);
        } catch (ConfirmationFailedException e) {
            model.addAttribute("failed", true);
        }

        model.addAttribute(new User());

        return "registration";
    }

    @RequestMapping(value = "/userPage", method = RequestMethod.GET)
    public String userPage(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByUserName(auth.getName());
        user.setPassword("");
        user.setPassword1("");

        model.addAttribute("pastOrders",
                orderService.getPastOrders(user));

        model.addAttribute("presentOrders",
                orderService.getPresentOrders(user));

        model.addAttribute("userData",user);

        model.addAttribute("cars",
                productService.getPagableCars());

        return "userPage";
    }

    @RequestMapping(value = "/userPage", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute("userData") @Valid FormUser formUser,
                             BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("error", "error");
            return "userPage";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByUserName(auth.getName());

        user.setAddress(formUser.getAddress());
        user.setBirth(formUser.getBirth());
        user.setTelephone(formUser.getTelephone());
        user.setPassword(formUser.getPassword());
        user.setPassword1(formUser.getPassword());

        return "redirect:/userPage";
    }

    @RequestMapping(value = "/userPage/changeEmail" , method = RequestMethod.GET)
    public String changeUserEmail(Model model){

        model.addAttribute("emailForm" , new EmailForm());

        return "changeEmail";
    }

    @RequestMapping(value = "/userPage/changeEmail" , method = RequestMethod.POST)
    public String updateEmail(@ModelAttribute("emailForm")@Valid EmailForm emailForm){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByUserName(auth.getName());
        user.setEmail(emailForm.getEmail());

        userService.saveUser(user);

        return "redirect:/userPage";
    }

    @InitBinder("newUser")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(recaptchaFormValidator);
    }
}
