package com.planus.user.controller;

import com.planus.user.dto.UserInfoResDTO;
import com.planus.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mypage")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity myInfo(@RequestParam long userId){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            UserInfoResDTO userInfo = userService.findUserInfo(userId);
            resultMap.put("result", userInfo);
            resultMap.put("message", "success");
            return new ResponseEntity(resultMap, HttpStatus.OK);
        }catch(Exception e){
            resultMap.put("message", "회원정보 조회에서 에러!");
            return new ResponseEntity(resultMap, HttpStatus.BAD_REQUEST);
        }
    }
}
