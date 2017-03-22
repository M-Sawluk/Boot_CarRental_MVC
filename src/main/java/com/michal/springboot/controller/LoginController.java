package com.michal.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login() {

        return "login";

    }

    @RequestMapping("/login-error.html")
    public String loginerror(Model model) {
        model.addAttribute("error", "true");
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(Model model) {
        return "welcome";
    }

    @RequestMapping("/403")
    public String denied(){
        return "403";
    }
}
