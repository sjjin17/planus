package com.planus.config;

import com.planus.login.handler.OAuth2AuthenticationSuccessHandler;
import com.planus.login.service.UserOauth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    private final UserOauth2Service userOauth2Service;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors().configurationSource(request -> {
            CorsConfiguration cors = new CorsConfiguration();
            //로컬
//            cors.setAllowedOrigins(Arrays.asList("http://localhost:8080", "http://localhost:8081"));
            cors.setAllowedOriginPatterns(Collections.singletonList("*"));
            cors.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE", "OPTIONS", "HEAD"));
//            cors.setAllowedMethods(Arrays.asList("*"));
//            cors.setAllowedHeaders(Arrays.asList("token", "Origin","Accept","X-Requested-With","Content-Type","Access-Control-Request-Method","Access-Control-Request-Headers","Authorization"));
            cors.setAllowedHeaders(Arrays.asList("*"));
            return cors;
        })
                .and()
                    .csrf().disable().headers().frameOptions().disable()
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers("/**").permitAll()
//                    .hasAuthority("ROLE_MEMBER").anyRequest().authenticated()
                .and().oauth2Login()
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .userInfoEndpoint().userService(userOauth2Service).and()
                .and().build();

    }
}
