package com.jhs.loginwithjson.filter.handler;

import com.jhs.loginwithjson.auth.CustomAuthorizationRequestRepository;
import com.jhs.loginwithjson.auth.utils.CookieUtils;
import com.jhs.loginwithjson.auth.model.CustomUser;
import com.jhs.loginwithjson.auth.utils.TokenMapping;
import com.jhs.loginwithjson.auth.jwt.JwtService;
import com.jhs.loginwithjson.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.jhs.loginwithjson.auth.CustomAuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final String TOKEN = "token";
    private static final String REFRESH_TOKEN = "refreshToken";

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final CustomAuthorizationRequestRepository customAuthorizationRequestRepository;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);
        TokenMapping tokenMapping = saveUser(authentication);
        getRedirectStrategy().sendRedirect(request, response, getRedirectUrl(targetUrl, tokenMapping));
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME).map(Cookie::getValue);
        clearAuthenticationAttributes(request, response);
        return redirectUri.orElse(getDefaultTargetUrl());
    }

    private TokenMapping saveUser(Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        String email = customUser.getEmail();
        TokenMapping token = jwtService.createToken(email);

        userRepository.findUserByEmail(email).get()
                .updateRefreshToken(token.getRefreshToken());
        return token;
    }

    private String getRedirectUrl(String targetUrl, TokenMapping token) {
        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam(TOKEN, token.getAccessToken())
                .queryParam(REFRESH_TOKEN, token.getRefreshToken())
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        customAuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}
