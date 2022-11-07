package com.planus.article.service;

import com.planus.article.dto.ArticleListResDTO;
import com.planus.article.dto.ArticleReqDTO;

import java.util.List;

public interface ArticleService {

    List<ArticleListResDTO> findAllArticles();

    String createArticle(ArticleReqDTO articleReqDTO);
}
