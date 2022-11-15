package com.planus.user.controller;

import com.planus.article.service.ArticleService;
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
    private final ArticleService articleService;

    public UserController(UserService userService, CommentService commentService, TokenProvider tokenProvider, ArticleService articleService) {
        this.userService = userService;
        this.commentService = commentService;
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity myInfo(@RequestHeader String Authorization){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            String token = Authorization.substring(7);
            UserInfoResDTO userInfo = userService.findUserInfo(token);
            resultMap.put("result", userInfo);
            resultMap.put("message", "success");
            return new ResponseEntity(resultMap, HttpStatus.OK);
        }catch(Exception e){
            resultMap.put("message", "회원정보 조회에서 에러!");
            return new ResponseEntity(resultMap, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/comment")
    public ResponseEntity getMyComment(@RequestHeader(name="Authorization") String token, @PageableDefault(size = 10, sort = "commentId", direction = Sort.Direction.DESC) Pageable pageable){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            resultMap.put("commentPage", commentService.getMyComment(token, pageable));
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
            return new ResponseEntity(resultMap, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/articles")
    public ResponseEntity getMyArticles(@RequestHeader(name="Authorization") String token, @PageableDefault(size=10, sort="articleId",direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(articleService.getMyArticles(token, pageable));
    }

    @GetMapping("/articles/like")
    public ResponseEntity getMyLikedArticles(@RequestHeader(name="Authorization") String token, @PageableDefault(size=10, sort="article_id", direction=Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(articleService.getMyLikedArticles(token, pageable));
    }

}
