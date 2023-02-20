package com.jhs.loginwithjson.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhs.loginwithjson.global.filter.JsonLoginProcessFilter;
import com.jhs.loginwithjson.global.filter.deprectaed.JsonToHttpRequestFilter;
import com.jhs.loginwithjson.global.filter.JwtAuthorizationFilter;
import com.jhs.loginwithjson.global.filter.handler.JwtProviderHandler;
import com.jhs.loginwithjson.global.auth.jwt.JwtService;
import com.jhs.loginwithjson.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
@RequiredArgsConstructor
public class SecurityFilterBeanConfig {

    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Bean
    public JsonLoginProcessFilter jsonLoginProcessFilter(JwtProviderHandler jwtProviderHandler) {
        JsonLoginProcessFilter jsonLoginProcessFilter = new JsonLoginProcessFilter(objectMapper, authenticationManager);
        jsonLoginProcessFilter.setAuthenticationSuccessHandler(jwtProviderHandler);
        return jsonLoginProcessFilter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtService, userRepository);
    }

    @Bean
    public JwtProviderHandler jwtProviderHandler() {
        return new JwtProviderHandler(jwtService, userRepository);
    }

    @Deprecated
    public JsonToHttpRequestFilter jsonToHttpRequestFilter() {
        return new JsonToHttpRequestFilter(objectMapper);
    }
}
