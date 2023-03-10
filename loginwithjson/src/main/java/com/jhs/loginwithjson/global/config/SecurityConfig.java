package com.jhs.loginwithjson.global.config;

import com.jhs.loginwithjson.global.auth.CookieAuthorizationRequestRepository;
import com.jhs.loginwithjson.global.auth.CustomOauth2UserService;
import com.jhs.loginwithjson.global.filter.JsonLoginProcessFilter;
import com.jhs.loginwithjson.global.filter.JwtAuthorizationFilter;
import com.jhs.loginwithjson.global.filter.handler.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JsonLoginProcessFilter jsonLoginProcessFilter;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    private final CustomOauth2UserService customOauth2UserService;
    private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors()
            .and()
                .httpBasic().disable()
                .rememberMe().disable()
                .headers().disable()
                .formLogin().disable()
                .requestCache().disable()
                .logout().disable()
                .exceptionHandling();
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeHttpRequests()
                .mvcMatchers("/loginhome").authenticated()
                .anyRequest().permitAll();
        http
                .oauth2Login()
                .authorizationEndpoint()
                .authorizationRequestRepository(cookieAuthorizationRequestRepository)
                .and()
                .userInfoEndpoint()
                .userService(customOauth2UserService)
            .and()
                .successHandler(oAuth2AuthenticationSuccessHandler);
        http
                .addFilterAfter(jsonLoginProcessFilter, OAuth2LoginAuthenticationFilter.class)
                .addFilterAfter(jwtAuthorizationFilter, JsonLoginProcessFilter.class);
        return http.build();
    }
}
