package com.jhs.loginwithnofront.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/loginhome")
    public String login(){
        return "loginHome";
    }
}
