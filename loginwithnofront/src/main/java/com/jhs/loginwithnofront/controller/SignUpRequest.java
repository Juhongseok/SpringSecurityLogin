package com.jhs.loginwithnofront.controller;

import com.jhs.loginwithnofront.domain.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class SignUpRequest {
    private String email;
    private String name;
    private String password;

    public User toEntity(PasswordEncoder passwordEncoder) {
        return new User(email, name, passwordEncoder.encode(password));
    }
}
