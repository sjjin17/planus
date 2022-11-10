package com.planus.article.controller;

import com.planus.article.dto.ArticleReqDTO;
import com.planus.article.service.ArticleService;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
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

    private final ArticleService articleService;

    @GetMapping
    public ResponseEntity getAllArticles() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(articleService.findAllArticles());
    }

    @PostMapping
    public ResponseEntity createArticle(@RequestBody @Valid ArticleReqDTO articleReqDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(articleService.createArticle(articleReqDTO));
    }

//    @GetMapping("/{article_id}")
//    public ResponseEntity getArticle(@PathVariable long articleId) {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(articleService.findOneArticle(articleId));
//    }

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
            resultMap.put("searchList", articleService.getArticleListByArea(token, area, pageable));
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
