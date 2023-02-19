package com.jhs.loginwithnofront.config;

import com.jhs.loginwithnofront.auth.CustomOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOauth2UserService customOauth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests()
                .mvcMatchers("/loginhome").authenticated()
                .anyRequest().permitAll();

        http.formLogin()
//                .loginPage("/login")
                /*.successHandler((request, response, authentication) -> {
                    response.sendRedirect("/loginhome");
                })*/
                .defaultSuccessUrl("/loginhome");

        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");

        http.oauth2Login()
                .userInfoEndpoint()
                .userService(customOauth2UserService);
        return http.build();
    }
}
