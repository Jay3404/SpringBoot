package com.bit.springboard.configuration;

import com.bit.springboard.handler.LoginFailureHandler;
import com.bit.springboard.oauth.OAuth2UserService;
import com.bit.springboard.oauth.provider.OAuth2UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private LoginFailureHandler loginFailureHandler;

    //소셜 로그인시 사용자 정보 받아서 처리해줄 Service
    @Autowired
    private OAuth2UserService oAuth2UserService;


    //비밀번호 암호화를 위한 PasswordEncoder
    //복호화가 불가능. match라는 메소드를 이용해서 사용자의 입력값과 DB의 저장값을 지교
    //=> true나 false리턴

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //필터 체인 구현(HttpSecurity 객체 사용)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                //csrf에 대한 공격 꺼두기
                .csrf(AbstractHttpConfigurer::disable)
                //요청 주소에 따른 권한 설정
                .authorizeHttpRequests((authorizeRequests) -> {
                    // '/'요청은 모든 사용자가 이용가능
                    authorizeRequests.requestMatchers("/").permitAll();
                    //css, js, images, upload 같은 정적 리소스들도 권한 처리 필수
                    authorizeRequests.requestMatchers("css/**").permitAll();
                    authorizeRequests.requestMatchers("js/**").permitAll();
                    authorizeRequests.requestMatchers("upload/**").permitAll();
                    authorizeRequests.requestMatchers("images/**").permitAll();

                    //게시판 기능은 권한을 가지고 있는 사용자만 이용가능
                    authorizeRequests.requestMatchers("/board/**").permitAll();
                    //관리자페이지는 관리자만 사용가능
                    authorizeRequests.requestMatchers("/admin/**").hasRole("ADMIN");
                    //회원가입, 로그인, 아이디 중복 체크 등 요청은 모든 사용자가 가능
                    authorizeRequests.requestMatchers("/user/join").permitAll();
                    authorizeRequests.requestMatchers("/user/login-view").permitAll();
                    authorizeRequests.requestMatchers("/user/join-view").permitAll();
                    authorizeRequests.requestMatchers("/user/id-check").permitAll();
                    authorizeRequests.requestMatchers("/api/**").permitAll();
                    authorizeRequests.anyRequest().authenticated();
                })
                //로그인, 로그아웃 설정
                //AuthenticationProvider에게 전달할 사용자 입력 아이디, 비밀번홍 대한 UsernamePasswordToken을 전달하는 상태까지 설정
                .formLogin((formLogin) -> {
                    //로그인 페이지 설정
                    formLogin.loginPage("/user/login-view");
                    //SpringSecurity에서 기본적으로 아이디는 username 비밀번호는 password로 사용
                    //어플리케이션에서 사용하는 아이디와 비밀번호 키값을 username과 password로 매핑
                    formLogin.usernameParameter("userId");
                    formLogin.passwordParameter("userPw");
                    //로그인 요청 url 매핑
                    //매핑된 url 요청이 왔을 때, Security에서 알아서 인증처리
                    formLogin.loginProcessingUrl("/user/loginProc");
                    //로그인 실패 핸들러 등록
                    formLogin.failureHandler(loginFailureHandler);
                    //로그인 성공 시 띄워줄 요청 url
                    formLogin.defaultSuccessUrl("/");

                })
                //Oauth2 기반 로그인 설정

                .oauth2Login((oauth2Login) -> {
                    oauth2Login.loginPage("/user/login-view");
                    oauth2Login.userInfoEndpoint(userInfoEndpointConfig -> {
                        userInfoEndpointConfig.userService(oAuth2UserService);
                    });
                })




                //로그아웃 처리
                .logout((logout) -> {
                    //로그아웃 URL 지정
                    logout.logoutUrl("/user/logout");
                    //사용자 인증정보가 저장된 시큐리티 컨텍스트가 세션에 저장되기 때문에 세션을 만료시켜서 컨텍스트를 제거한다.
                    logout.invalidateHttpSession(true);
                    //로그아웃 성공 시 호출할 요청 지정
                    logout.logoutSuccessUrl("/user/login-view");
                })
                .build();
    }







}
