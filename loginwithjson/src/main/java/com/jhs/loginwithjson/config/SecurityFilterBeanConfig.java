package com.jhs.loginwithjson.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhs.loginwithjson.filter.JsonLoginProcessFilter;
import com.jhs.loginwithjson.filter.JsonToHttpRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
@RequiredArgsConstructor
public class SecurityFilterBeanConfig {

    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;

    @Bean
    public JsonLoginProcessFilter jsonLoginProcessFilter() {
        JsonLoginProcessFilter jsonLoginProcessFilter = new JsonLoginProcessFilter(objectMapper);
        jsonLoginProcessFilter.setAuthenticationManager(authenticationManager);
        jsonLoginProcessFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            response.getWriter().println("Success Login");
        });
        return jsonLoginProcessFilter;
    }

    @Bean
    public JsonToHttpRequestFilter jsonToHttpRequestFilter() {
        return new JsonToHttpRequestFilter(objectMapper);
    }
}
