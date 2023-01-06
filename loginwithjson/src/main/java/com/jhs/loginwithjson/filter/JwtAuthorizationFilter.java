package com.jhs.loginwithjson.filter;

import com.jhs.loginwithjson.domain.CustomUser;
import com.jhs.loginwithjson.domain.User;
import com.jhs.loginwithjson.jwt.JwtService;
import com.jhs.loginwithjson.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    /**
     * accessToken 유효 -> authentication 저장
     * accessToken 만료
     *      refreshToken 유효 -> authentication 저장, accessToken 갱신
     *      refreshToken 만료 -> throw Exception
     */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        jwtService.extractAccessToken(request)
                .filter(jwtService::isTokenValid)
                .ifPresentOrElse(
                        this::saveAuthentication,
                        () -> checkRefreshToken(request, response)
                );
        filterChain.doFilter(request, response);
    }

    private void saveAuthentication(String accessToken) {
        String email = jwtService.extractUserEmail(accessToken);
        CustomUser customUser = new CustomUser(userRepository.findUserByEmail(email));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(customUser, null, customUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void checkRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> refreshToken = jwtService.extractRefreshToken(request)
                .filter(jwtService::isTokenValid);

        if (refreshToken.isPresent()) {
            User user = userRepository.findUserByRefreshToken(refreshToken.get())
                    .orElseThrow(IllegalArgumentException::new);
            String accessToken = jwtService.createAccessToken(user.getEmail());
            jwtService.setAccessTokenInHeader(response, accessToken);
            saveAuthentication(accessToken);
        }
    }
}
