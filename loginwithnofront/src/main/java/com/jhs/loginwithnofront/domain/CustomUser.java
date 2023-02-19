package com.jhs.loginwithnofront.domain;

import com.jhs.loginwithnofront.auth.userinfo.OAuth2UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CustomUser implements UserDetails, OAuth2User {

    private final User user;
    private OAuth2UserInfo oauth2Oauth2UserInfo;

    public CustomUser(User user) {
        this.user = user;
    }

    public CustomUser(User user, OAuth2UserInfo oauth2Oauth2UserInfo) {
        this.user = user;
        this.oauth2Oauth2UserInfo = oauth2Oauth2UserInfo;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2Oauth2UserInfo.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_USER");
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return oauth2Oauth2UserInfo.getName();
    }
}
