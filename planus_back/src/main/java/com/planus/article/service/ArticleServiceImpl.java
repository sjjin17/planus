package com.planus.article.service;


import com.planus.article.dto.ArticleListResDTO;
import com.planus.article.dto.ArticleReqDTO;
import com.planus.db.entity.Article;
import com.planus.db.repository.ArticleRepository;
import com.planus.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final TokenProvider tokenProvider;
    @Override
    public List<ArticleListResDTO> findAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream().map(article -> ArticleListResDTO.toResDTO(article)).collect(Collectors.toList());
    }

    @Override
    public String createArticle(ArticleReqDTO articleReqDTO) {
        // user검증

        // 내 모든 여행이 조회 가능해야 함 -> 따로 api 만들어야 함
        // articleReqDTO -> article
        Article article = ArticleReqDTO.toEntity(articleReqDTO);
        articleRepository.save(article);
        return article.getTitle();
    }
}
