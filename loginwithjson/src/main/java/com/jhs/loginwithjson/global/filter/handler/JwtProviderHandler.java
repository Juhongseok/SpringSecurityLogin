package com.jhs.loginwithjson.global.filter.handler;

import com.jhs.loginwithjson.global.auth.model.SecurityUser;
import com.jhs.loginwithjson.global.auth.model.TokenMapping;
import com.jhs.loginwithjson.global.auth.utils.JwtService;
import com.jhs.loginwithjson.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtProviderHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        String email = securityUser.getEmail();

        TokenMapping token = jwtService.createToken(email);
        jwtService.sendBothToken(response, token.getAccessToken(), token.getRefreshToken());
        userRepository.findUserByEmail(email).get()
                .updateRefreshToken(token.getRefreshToken());
    }
}
