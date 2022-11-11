package com.planus.article.controller;

import com.planus.article.dto.ArticleReqDTO;
import com.planus.article.service.ArticleService;
import com.planus.community.controller.CommentController;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleService articleService;

    @GetMapping
    public ResponseEntity getAllArticles(@PageableDefault(size = 6, sort = "regDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(articleService.findAllArticles(pageable));
    }

    @PostMapping
    public ResponseEntity createArticle(@RequestHeader(name="Authorization") String token, @RequestBody @Valid ArticleReqDTO articleReqDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(articleService.createArticle(token, articleReqDTO));
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity deleteArticle(@RequestHeader(name="Authorization") String token, @PathVariable long articleId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(articleService.deleteArticle(token, articleId));

    }

    @PutMapping("/{articleId}")
    public ResponseEntity updateArticle(@RequestHeader(name="Authorization")String token, @RequestBody ArticleReqDTO articleReqDTO, @PathVariable long articleId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(articleService.updateArticle(token, articleReqDTO, articleId));

    }

    @GetMapping("/{articleId}")
    public ResponseEntity getArticle(@PathVariable long articleId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(articleService.findOneArticle(articleId));
    }


    @GetMapping("/title")
    public ResponseEntity getArticleListByTitle(@RequestHeader(required = false) String token, @RequestParam String title, @PageableDefault(size = 6, sort = "regDate", direction = Sort.Direction.DESC) Pageable pageable){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap.put("searchList", articleService.getArticleListByTitle(token, title, pageable));
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/area")
    public ResponseEntity getArticleListByArea(@RequestHeader(required = false) String token, @RequestParam(value = "area") int[] area, @PageableDefault(size = 6, sort = "reg_date", direction = Sort.Direction.DESC) Pageable pageable){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap.put("articleList", articleService.getArticleListByArea(token, area, pageable));
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/area/length")
    public ResponseEntity getArticleListByAreaLength(@RequestParam(value = "area") int[] area){

        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap.put("length", articleService.countPage(area));
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/like/{articleId}")
    public ResponseEntity likeArticle(@RequestHeader(name="Authorization") String token, @PathVariable long articleId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(articleService.likeArticle(token, articleId));
    }


}
