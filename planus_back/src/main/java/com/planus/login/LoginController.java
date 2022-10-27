package com.planus.login;

import com.planus.db.entity.User;
import com.planus.login.util.JwtUtil;
import com.planus.login.util.KakaoUtil;
import com.planus.user.dto.UserInfoResDTO;
import com.planus.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final KakaoUtil kakaoUtil;
    private final UserService userService;

    private final JwtUtil jwtUtil;

    @GetMapping("/oauth2/kakao")
    public ResponseEntity<String> Login(@RequestParam(name="code") String code){
        String accessToken = kakaoUtil.getAccessToken(code);
        Map<String, Object> userInfo = kakaoUtil.getUserInfo(accessToken);
        User loginUser = userService.join((String) userInfo.get("nickname"), (String) userInfo.get("email"), (Long) userInfo.get("id"));
        String token = jwtUtil.createToken(loginUser.getUserId(), loginUser.getName(), loginUser.getEmail());
        return new ResponseEntity<>(token, OK);
    }

    @GetMapping("/test")
    public void Test(@RequestHeader(name="token") String token){
        long userIdFromToken = jwtUtil.getUserIdFromToken(token);
        UserInfoResDTO userInfo = userService.findUserInfo(userIdFromToken);
        System.out.println("Welcome! "+ userInfo.getNickname());
    }

    @GetMapping("/test2")
    public ResponseEntity Test2(@RequestHeader(name="token") String token){
        long userIdFromToken = jwtUtil.getUserIdFromToken(token);
        UserInfoResDTO userInfo = userService.findUserInfo(userIdFromToken);
        System.out.println("Welcome2222! "+ userInfo.getNickname());
        return new ResponseEntity(OK);
    }
}
