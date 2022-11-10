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

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

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

//    @GetMapping("/{article_id}")
//    public ResponseEntity getArticle(@PathVariable long articleId) {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(articleService.findOneArticle(articleId));
//    }
}
