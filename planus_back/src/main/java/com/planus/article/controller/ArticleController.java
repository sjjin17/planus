package com.planus.article.controller;

import com.planus.article.dto.ArticleReqDTO;
import com.planus.article.service.ArticleService;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity getAllArticles() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(articleService.findAllArticles());
    }

    @PostMapping
    public ResponseEntity createArticle(@RequestBody @Valid ArticleReqDTO articleReqDTO) {
        System.out.println(1);
        return ResponseEntity.status(HttpStatus.OK)
                .body(articleService.createArticle(articleReqDTO));
    }
}
