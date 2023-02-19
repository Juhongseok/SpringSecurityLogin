package com.jhs.loginwithnofront.auth.userinfo;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuthAttributes {
    GOOGLE("google", attributes -> new OAuth2UserInfo(
            attributes,
            attributes.get("sub").toString(),
            attributes.get("name").toString(),
            attributes.get("email").toString()
    )),

    NAVER("naver", attributes -> {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return new OAuth2UserInfo(
                response,
                response.get("id").toString(),
                response.get("name").toString(),
                response.get("email").toString()
        );
    }),
    ;

    private final String registrationId;
    private final Function<Map<String, Object>, OAuth2UserInfo> of;

    OAuthAttributes(String registrationId, Function<Map<String, Object>, OAuth2UserInfo> of) {
        this.registrationId = registrationId;
        this.of = of;
    }

    public static OAuth2UserInfo of(String providerId, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> provider.registrationId.equals(providerId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .of.apply(attributes);
    }
}
