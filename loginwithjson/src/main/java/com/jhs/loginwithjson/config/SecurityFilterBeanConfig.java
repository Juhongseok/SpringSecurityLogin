package com.jhs.loginwithjson.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhs.loginwithjson.filter.JsonLoginProcessFilter;
import com.jhs.loginwithjson.filter.JsonToHttpRequestFilter;
import com.jhs.loginwithjson.filter.handler.JwtProviderHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
@RequiredArgsConstructor
public class SecurityFilterBeanConfig {

    private final ObjectMapper objectMapper;
    private final JwtProviderHandler jwtProviderHandler;
    private final AuthenticationManager authenticationManager;

    @Bean
    public JsonLoginProcessFilter jsonLoginProcessFilter() {
        JsonLoginProcessFilter jsonLoginProcessFilter = new JsonLoginProcessFilter(objectMapper, authenticationManager);
        jsonLoginProcessFilter.setAuthenticationSuccessHandler(jwtProviderHandler);
        return jsonLoginProcessFilter;
    }

    @Bean
    public JsonToHttpRequestFilter jsonToHttpRequestFilter() {
        return new JsonToHttpRequestFilter(objectMapper);
    }
}
