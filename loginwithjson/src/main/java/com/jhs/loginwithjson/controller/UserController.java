package com.jhs.loginwithjson.controller;

import com.jhs.loginwithjson.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signUp")
    public String signUp(@RequestBody SignUpRequest request){
        System.out.println("UserController.signUp SignUpRequest: " + request);
        userService.saveUSer(request);
        return "Success";
    }

    @GetMapping("/loginhome")
    public String loginHome(){
        return "Can Access With Authority";
    }
}
