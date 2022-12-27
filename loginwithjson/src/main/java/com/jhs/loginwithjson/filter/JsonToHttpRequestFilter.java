package com.jhs.loginwithjson.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JsonToHttpRequestFilter implements Filter {

    private static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    private static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
    private final ObjectMapper objectMapper;
    private RequestMatcher matcher;

    public JsonToHttpRequestFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.matcher = new AntPathRequestMatcher("/login", "POST");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpRequestWithModifiableParameters request = new HttpRequestWithModifiableParameters((HttpServletRequest) req);

        if (matcher.matches(request)) {
            LoginDto loginData = objectMapper.readValue(request.getInputStream(), LoginDto.class);

            request.setParameter(SPRING_SECURITY_FORM_USERNAME_KEY, loginData.getUserId());
            request.setParameter(SPRING_SECURITY_FORM_PASSWORD_KEY, loginData.getPassword());
        }
        chain.doFilter(request, resp);
    }

    @Setter @Getter
    @NoArgsConstructor
    private static class LoginDto {
        private String userId;
        private String password;
    }
}
