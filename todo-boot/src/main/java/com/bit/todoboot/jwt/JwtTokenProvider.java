package com.bit.todoboot.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtTokenProvider {
//    JWT Token의 signature의 유효성 검사에 사용될 키 값.
//    navercloud5todobootappgogibitcamp702
    private static final String SECRET_KEY = "bmF2ZXJjbG91ZDV0b2RvYm9vdGFwcGdvZ2liaXRjYW1wNzAy";
//    SECRET_KEY를 Key객체로 변환
    Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    /*
    * 사용자 정보를 받아서 JWT Token을 생성해주는 메소드
    * JSON 문자열을 Base64 인코딩하고 뒷부분에 서버에 있는 시그니쳐(SECRET_KEY)를 Hashing해서 추가
    * */
    public String create(Member member) {
//        토큰 만료일 설정. 현재로부터 1일뒤로 설정
        Date expireDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));

//        JWT Token 생성하여 반환
        return Jwts.builder()
//                시그니쳐(서명)부분에 들어갈 key값 지정
                .signWith(key, SignatureAlgorithm.HS256)
//                페이로드에 들어갈 내용
//                토큰의 주인(sub)
                .setSubject(member)
//                토큰 발행주체(iss)
                .setIssuer("todo boot app")
//                토큰 발행일자(isa)
                .setIssuedAt(new Date())
//                토큰 만료일자
                .setExpiration(expireDate)
                .compact();
    }
}