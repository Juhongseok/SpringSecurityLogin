package com.jhs.loginwithjson.filter;

import com.jhs.loginwithjson.domain.CustomUser;
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

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    /**
     * 처리 방안에 대해서 더 고민해 볼 것
     * accessToken 유효, refreshToken 유효 -> authentication 저장
     * accessToken 만료, refreshToken 유효 -> authentication 저장, accessToken 갱신
     * accessToken 유효, refreshToken 만료 -> authentication 저장, refreshToken 갱신
     * accessToken 만료, refreshToken 만료 -> 접근 불가
     */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        jwtService.extractAccessToken(request)
                .filter(jwtService::isTokenValid)
                .ifPresentOrElse(
                        accessToken -> saveAuthentication(accessToken),
                        () -> checkRefreshToken(request)
                );
    }

    private void saveAuthentication(String accessToken) {
        String email = jwtService.extractUserEmail(accessToken);
        CustomUser customUser = new CustomUser(userRepository.findUserByEmail(email));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(customUser, null, customUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void checkRefreshToken(HttpServletRequest request) {
        jwtService.extractRefreshToken(request)
                .filter(jwtService::isTokenValid)
                .ifPresentOrElse(
                        //refreshToken 유효 ->
                        refreshToken -> System.out.println(),
                        //refreshToken 만료 ->
                        () -> System.out.println()
                );
    }
}
