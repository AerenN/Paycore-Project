package com.creditcalculator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UIController {

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String getHomePage(){
        return "index";
    }

    @RequestMapping(value = "/api/auth/signup", method = RequestMethod.GET)
    public String getSignupForm(){
        return "signup";
    }

    @RequestMapping(value = "/api/auth/signin", method = RequestMethod.GET)
    public String getSigninPage(){
        return "signin";
    }
}
