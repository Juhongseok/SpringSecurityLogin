package com.jhs.loginwithnofront.auth.userinfo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class OAuth2UserInfo {
    private final Map<String, Object> attributes;
    private final String providerId;
    private final String name;
    private final String email;
}
