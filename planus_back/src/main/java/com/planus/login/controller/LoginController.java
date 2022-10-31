package com.planus.login.controller;

import com.planus.login.util.KakaoUtil;
import com.planus.util.JwtUtil;
import com.planus.user.dto.UserInfoResDTO;
import com.planus.user.service.UserService;
import com.planus.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

    private final JwtUtil jwtUtil;

    private final TokenProvider tokenProvider;

    private final KakaoUtil kakaoUtil;

//    @GetMapping("/oauth2/kakao")
//    public ResponseEntity<String> Login(@RequestParam(name="code") String code){
//        System.out.println(code);
////        String accessToken = kakaoUtil.getAccessToken(code);
////        Map<String, Object> userInfo = kakaoUtil.getUserInfo(accessToken);
////        User loginUser = userService.join((String) userInfo.get("nickname"), (String) userInfo.get("email"), (Long) userInfo.get("id"));
////        String token = jwtUtil.createToken(loginUser.getUserId(), loginUser.getName(), loginUser.getEmail());
////        return new ResponseEntity<>(token, OK);
//        return null;
//    }

    @GetMapping("/test")
    public void Test(@RequestHeader String Authorization){
        String token=Authorization.split(" ")[1];
        //TODO jwtprovider와 jwtUtil 통합할것
        long userIdFromToken = tokenProvider.getUserId(token);
        UserInfoResDTO userInfo = userService.findUserInfo(userIdFromToken);
        System.out.println("Welcome! "+ userInfo.getNickname());
    }

    @GetMapping("/test2")
    public void Test2(@RequestHeader String Authorization){
        String token=Authorization.split(" ")[1];
        long userIdFromToken = tokenProvider.getUserId(token);
        UserInfoResDTO userInfo = userService.findUserInfo(userIdFromToken);
        System.out.println("Welcome2222! "+ userInfo.getNickname());
    }

    @GetMapping("/signout")
    public void signOut(@RequestHeader String Authorization, HttpServletResponse response) throws IOException {
        String token = Authorization.split(" ")[1];
        long userIdFromToken = tokenProvider.getUserId(token);
        long kakaoId = userService.findKakaoIdByUserId(userIdFromToken);
        System.out.println(kakaoId);
        kakaoUtil.kakaoSignOut(kakaoId);
        userService.deleteUser(userIdFromToken);
        //TODO 로컬

//        response.sendRedirect("http://localhost:8081/");
        response.sendRedirect("https://k7a505.p.ssafy.io/");
    }
}
