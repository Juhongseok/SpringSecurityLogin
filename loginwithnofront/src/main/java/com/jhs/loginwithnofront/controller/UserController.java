package com.jhs.loginwithnofront.controller;

import com.jhs.loginwithnofront.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/signUp")
    public String signUpPage(){
        return "signUp";
    }

    @PostMapping("/signUp")
    public String signUp(SignUpRequest request){
        System.out.println(request);
        userService.saveUSer(request);
        return "redirect:/";
    }

    @GetMapping("/loginPage")
    public String loginPage(){
        return "login";
    }
}
