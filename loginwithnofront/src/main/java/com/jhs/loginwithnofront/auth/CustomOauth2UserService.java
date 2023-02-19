package com.jhs.loginwithnofront.auth;

import com.jhs.loginwithnofront.auth.userinfo.OAuth2UserInfo;
import com.jhs.loginwithnofront.auth.userinfo.OAuthAttributes;
import com.jhs.loginwithnofront.domain.CustomUser;
import com.jhs.loginwithnofront.domain.User;
import com.jhs.loginwithnofront.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String PASSWORD = "password";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = OAuthAttributes.of(registrationId, super.loadUser(userRequest).getAttributes());
        User user = saveUser(oAuth2UserInfo);
        return new CustomUser(user, oAuth2UserInfo);
    }

    private User saveUser(OAuth2UserInfo oauth2Oauth2UserInfo) {
        String email = oauth2Oauth2UserInfo.getEmail();
        String name = oauth2Oauth2UserInfo.getName();
        String password = passwordEncoder.encode(PASSWORD + UUID.randomUUID().toString().substring(0, 8));

        User user = new User(email, name, password);
        userRepository.findUserByEmail(email)
                .ifPresentOrElse(
                        entity -> entity.update(user.getEmail(), user.getName()),
                        () -> userRepository.save(user)
                );
        return user;
    }
}
