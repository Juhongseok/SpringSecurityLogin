package com.jhs.loginwithjson.config;

import com.jhs.loginwithjson.filter.JsonLoginProcessFilter;
import com.jhs.loginwithjson.filter.JsonToHttpRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JsonLoginProcessFilter jsonLoginProcessFilter;
    private final JsonToHttpRequestFilter jsonToHttpRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests()
                .mvcMatchers("/loginhome").authenticated()
                .anyRequest().permitAll();

        /*http.formLogin()
                .loginPage("/login")
                .successHandler((request, response, authentication) -> {
                    response.getWriter().println("success login");
                });

        http.addFilterAfter(jsonToHttpRequestFilter, LogoutFilter.class);*/
        http.addFilterAfter(jsonLoginProcessFilter, LogoutFilter.class);
        return http.build();
    }
}
