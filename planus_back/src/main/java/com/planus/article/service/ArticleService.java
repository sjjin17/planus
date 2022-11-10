package com.planus.article.service;

import com.planus.article.dto.ArticleListResDTO;
import com.planus.article.dto.ArticleReqDTO;
import com.planus.article.dto.ArticleResDTO;
import org.springframework.data.domain.Pageable;
import com.planus.article.dto.SearchResDTO;
import com.planus.db.entity.Article;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {

    ArticleResDTO findAllArticles(Pageable pageable);

    long createArticle(String token, ArticleReqDTO articleReqDTO);

    //ArticleResDTO findOneArticle(long articleId);

    SearchResDTO getArticleListByTitle(String token, String title, Pageable pageable);
    SearchResDTO getArticleListByArea(String token, int[] area, Pageable pageable);
}
