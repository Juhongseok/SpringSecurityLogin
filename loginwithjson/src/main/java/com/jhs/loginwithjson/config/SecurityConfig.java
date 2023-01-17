package com.jhs.loginwithjson.config;

import com.jhs.loginwithjson.filter.JwtAuthenticationFilter;
import com.jhs.loginwithjson.filter.JsonToHttpRequestFilter;
import com.jhs.loginwithjson.filter.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jsonLoginProcessFilter;
    private final JsonToHttpRequestFilter jsonToHttpRequestFilter;

    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Value("${jwt.access.header}")
    private String accessHeader;
    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000", "http://127.0.0.1:5500"));
        configuration.setAllowedMethods(List.of("GET","POST"));
        configuration.addAllowedHeader("*");
        configuration.setExposedHeaders(List.of(accessHeader, refreshHeader));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors();
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
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
        http.addFilterAfter(jwtAuthorizationFilter, JwtAuthenticationFilter.class);
        return http.build();
    }
}
