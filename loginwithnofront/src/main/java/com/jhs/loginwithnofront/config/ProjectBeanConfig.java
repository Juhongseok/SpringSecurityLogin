package com.jhs.loginwithnofront.config;

import com.jhs.loginwithnofront.auth.CustomAuthenticationProvider;
import com.jhs.loginwithnofront.repository.UserRepository;
import com.jhs.loginwithnofront.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ProjectBeanConfig {

    private final UserRepository userRepository;

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userService());
        return provider;
    }

    /*@Bean
    public AuthenticationProvider authenticationProvider(){
        return new CustomAuthenticationProvider(userService(), passwordEncoder());
    }*/

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository, passwordEncoder());
    }
}
