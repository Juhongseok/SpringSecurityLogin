package com.jhs.loginwithjson.filter.handler;

import com.jhs.loginwithjson.domain.CustomUser;
import com.jhs.loginwithjson.jwt.JwtService;
import com.jhs.loginwithjson.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtProviderHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        String email = customUser.getEmail();

        String accessToken = jwtService.createAccessToken(email);
        String refreshToken = jwtService.createRefreshToken();

        jwtService.sendBothToken(response, accessToken, refreshToken);

        userRepository.findUserByEmail(email)
                .updateRefreshToken(refreshToken);
    }
}
