package com.planus.login.service;

import com.planus.db.entity.User;
import com.planus.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class UserOauth2Service extends DefaultOAuth2UserService {

    private final HttpSession httpSession;
    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        Map<String,Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        long id = (long) attributes.get("id");
        String email = (String) kakaoAccount.get("email");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        String nickname = (String) profile.get("nickname");
        String imageUrl = (String) profile.get("profile_image_url");

        User user = userService.join(nickname, email, id, imageUrl);

        profile.put("nickname", user.getName());
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_MEMBER")),attributes, "id");
    }
}
