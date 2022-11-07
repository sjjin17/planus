package com.planus.user.controller;

import com.planus.community.service.CommentService;
import com.planus.user.dto.UserInfoResDTO;
import com.planus.user.service.UserService;
import com.planus.util.TokenProvider;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mypage")
public class UserController {

    private final UserService userService;
    private final CommentService commentService;
    private final TokenProvider tokenProvider;

    public UserController(UserService userService, CommentService commentService, TokenProvider tokenProvider) {
        this.userService = userService;
        this.commentService = commentService;
        this.tokenProvider = tokenProvider;
    }

    @GetMapping
    public ResponseEntity myInfo(@RequestHeader String Authorization){
        String token = Authorization.substring(7);
        long userId = tokenProvider.getUserId(token);
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

    @GetMapping("/comment")
    public ResponseEntity getMyComment(@RequestHeader(name="Authorization") String token, @PageableDefault(size = 10, sort = "regDate", direction = Sort.Direction.DESC) Pageable pageable){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            resultMap.put("commentList", commentService.getMyComment(token, pageable));
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity modifyNickname(@RequestHeader String Authorization, @RequestBody Map<String, String> body){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            String nickname = body.get("nickname");
            String token = Authorization.substring(7);
            String newToken = userService.updateUser(token, nickname);
            resultMap.put("message", "success");
            resultMap.put("newToken", newToken);
            return new ResponseEntity(resultMap, HttpStatus.OK);
        }catch(Exception e){
            resultMap.put("message", "회원정보 수정에서 에러");
            System.out.println("회원정보 수정에서 에러");
            e.printStackTrace();
            return new ResponseEntity(resultMap, HttpStatus.BAD_REQUEST);
        }
    }
}
