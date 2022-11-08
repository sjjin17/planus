package com.planus.article.service;

import com.planus.article.dto.ArticleListResDTO;
import com.planus.article.dto.ArticleReqDTO;
import com.planus.article.dto.ArticleResDTO;
import com.planus.db.entity.Article;

import java.util.List;

public interface ArticleService {

    List<ArticleListResDTO> findAllArticles();

    long createArticle(ArticleReqDTO articleReqDTO);

    //ArticleResDTO findOneArticle(long articleId);
}
