package com.planus.login.handler;

import com.planus.util.JwtUtil;
import com.planus.user.service.UserService;
import com.planus.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        long kakaoId = (long) attributes.get("id");

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        String email = (String) kakaoAccount.get("email");
        String nickname = (String) ((Map<String, Object>) kakaoAccount.get("profile")).get("nickname");
        long userId = userService.findUserByKakaoId(kakaoId).getUserId();
        System.out.println("카카오에서 정보 받기 성공" + nickname + " " + email);
        String jwtToken = tokenProvider.createToken(authentication, userId, nickname, email);
//        String jwtToken = jwtUtil.createToken(userId, nickname, email);
        System.out.println("토큰 생성 성공" + jwtToken);
        if (response.isCommitted()) {
            return;
        }
        getRedirectStrategy().sendRedirect(request, response, makeRedirectUrl(jwtToken));
    }

    private String makeRedirectUrl(String token) {
        //로컬
        return UriComponentsBuilder.fromUriString("http://localhost:8081/login/getkakaotoken?token=" + token).build().toUriString();
    }
}
