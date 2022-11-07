package com.planus.community.controller;

import com.planus.community.dto.CommentReqDTO;
import com.planus.community.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity addComment(@RequestHeader(name="Authorization") String token, @RequestBody CommentReqDTO commentReqDTO){
        long articleId = commentReqDTO.getId();
        String content = commentReqDTO.getContent();

        Map<String, Object> resultMap = new HashMap<>();
        try{
            resultMap.put("commentId", commentService.addComment(token, articleId, content));
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity modifyComment(@RequestHeader(name="Authorization") String token, @RequestBody CommentReqDTO commentReqDTO){
        long commentId = commentReqDTO.getId();
        String content = commentReqDTO.getContent();

        Map<String, Object> resultMap = new HashMap<>();
        try{
            resultMap.put("commentId", commentService.modifyComment(token, commentId, content));
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity getArticleComment(@RequestParam long articleId, @PageableDefault(size = 10, sort = "regDate", direction = Sort.Direction.DESC) Pageable pageable){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            resultMap.put("commentList", commentService.getArticleComment(articleId, pageable));
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity delComment(@RequestHeader(name="Authorization") String token, @RequestParam long commentId){
        try{
            commentService.delComment(token, commentId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
