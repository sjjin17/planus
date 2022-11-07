package com.planus.login.filter;

import com.planus.util.TokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {

    private final String Header= "Authorization";

    private final TokenProvider tokenProvider;

    public JwtFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String jwt = resolveToken(httpServletRequest);
        if(((HttpServletRequest) request).getMethod().equals("OPTIONS")){
            chain.doFilter(request, response);
        }else {
            try {
                Authentication authentication = tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (SecurityException | MalformedJwtException | SignatureException e) {
                request.setAttribute("exception", "JWT 서명 문제");
            } catch (ExpiredJwtException e) {
                request.setAttribute("exception", "만료된 JWT");
            } catch (UnsupportedJwtException e) {
                request.setAttribute("exception", "지원하지 않는 토큰형식");
            } catch (IllegalArgumentException e) {
                request.setAttribute("exception", "JWT 토큰 없음");
            }
            chain.doFilter(request, response);
        }
    }

    private String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(Header);
        if(StringUtils.hasText(bearerToken)&&bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
