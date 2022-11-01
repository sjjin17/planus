package com.planus.util;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.property.access.internal.PropertyAccessMapImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider {
    private static String secretKey = "ahf?fn";

    private static Long accessTokenTime = 60*60*24*30*1000L;


    public String createToken(Authentication authentication, long userId, String nickname, String email){
        String authorities = authentication
                .getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Long now = new Date().getTime();
        Date validity = new Date(now + this.accessTokenTime);

        String token = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .claim("userId", String.valueOf(userId))
                .claim("nickname", nickname)
                .claim("email", email)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .setExpiration(validity)
                .compact();
        System.out.println("create token "+ token);
        return token;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth")
                        .toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(),"",authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);

    }

    public boolean validateToken(String token){
        try{
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
            return true;
        }catch(SecurityException | MalformedJwtException e){
            System.out.println("JWT 서명 문제");
        }catch(ExpiredJwtException e){
            System.out.println("jwt 만료");
        }catch(UnsupportedJwtException e){
            System.out.println("지원하지 않는 토큰??");
        }catch(IllegalArgumentException e){
            System.out.println("JWT 토큰 잘못됨");
        }
        return false;
    }

    public long getUserId(String token){
        try{
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
            return Long.parseLong( String.valueOf(claims.get("userId")));
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;
    }

    public String updateTokenNickname(String token, String newNickname){
        Long now = new Date().getTime();
        Date validity = new Date(now + this.accessTokenTime);
        Claims claims = Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        String newToken = Jwts.builder()
                .setClaims(claims)
                .claim("nickname", newNickname)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .setExpiration(validity)
                .compact();

        return newToken;
    }
}
