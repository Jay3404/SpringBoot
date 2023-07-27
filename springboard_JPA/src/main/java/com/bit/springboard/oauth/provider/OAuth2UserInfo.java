package com.bit.springboard.oauth.provider;

//여러가지 소셜 로그인을 구현하기 위해 인터페이스로 구현
public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();
}
