package com.jhs.loginwithjson.domain;

import com.jhs.loginwithjson.controller.SignUpRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String email;
    private String name;
    private String password;
    private String refreshToken;

    public User(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public static User of(SignUpRequest request) {
        return new User(request.getEmail(), request.getName(), request.getPassword());
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken.replace("Bearer ", "");
    }

    public void update(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
