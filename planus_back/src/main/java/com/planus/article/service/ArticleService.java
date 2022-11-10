package com.planus.article.service;

import com.planus.article.dto.ArticleListResDTO;
import com.planus.article.dto.ArticleReqDTO;
import com.planus.article.dto.ArticleResDTO;
import org.springframework.data.domain.Pageable;
import com.planus.article.dto.SearchResDTO;
import com.planus.db.entity.Article;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ArticleService {

    ArticleResDTO findAllArticles(Pageable pageable);

    long createArticle(String token, ArticleReqDTO articleReqDTO);

    //ArticleResDTO findOneArticle(long articleId);

    Map<String, String> deleteArticle(String token, long articleId);

    // article상세 조회 끝나고 returnType 변경
    long updateArticle(String token, ArticleReqDTO articleReqDTO, long articleId);

    SearchResDTO getArticleListByTitle(String token, String title, Pageable pageable);
    SearchResDTO getArticleListByArea(String token, int[] area, Pageable pageable);
}
