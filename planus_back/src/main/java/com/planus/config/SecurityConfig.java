package com.planus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors().configurationSource(request -> {
            CorsConfiguration cors = new CorsConfiguration();
            cors.setAllowedOrigins(Arrays.asList("http://localhost:8081", "http://localhost:8080", "https://k7a505.p.ssafy.io"));
            cors.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE", "OPTIONS"));
            cors.setAllowedHeaders(Arrays.asList("token","content-type"));
            cors.setAllowCredentials(true);
            return cors;
        })
                .and().csrf().disable().headers().frameOptions().disable()
                .and().authorizeRequests()
                .antMatchers("/**").permitAll()
                .and().oauth2Login()
                .and().build();
    }
}
