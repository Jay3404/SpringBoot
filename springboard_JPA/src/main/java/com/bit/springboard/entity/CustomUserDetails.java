package com.bit.springboard.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomUserDetails implements UserDetails, OAuth2User {

    private User user;

    //소셜 로그인시 사용자 정보를 담아줄 Map 선언
    Map<String, Object> attributes;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();

        auths.add(
                new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return user.getRole();
                    }
                }
        );

        return auths;
    }

    //비밀번호 제공 메소드
    @Override
    public String getPassword() {
        return this.user.getUserPw();
    }

    //아이디 제공 메소드
    @Override
    public String getUsername() {
        return this.user.getUserId();
    }

    //계정 만료 여부
    //사용 안할 때는 항상 True
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정 잠김 여부
    //사용안할 때는 True
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //비밀번호 만료 여부
    //사용안할 때는 True
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정 활성화 여부
    @Override
    public boolean isEnabled() {
        return true;
    }

    //소셜 로그인 정보를 리턴해주는 메소드
    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public String getName() {
        return null;
    }

}
