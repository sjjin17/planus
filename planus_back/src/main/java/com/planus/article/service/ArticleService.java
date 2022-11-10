package com.planus.article.service;

import com.planus.article.dto.ArticleListResDTO;
import com.planus.article.dto.ArticleReqDTO;
import com.planus.article.dto.ArticleResDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {

    ArticleResDTO findAllArticles(Pageable pageable);

    long createArticle(String token, ArticleReqDTO articleReqDTO);

//    ArticleResDTO findOneArticle(long articleId);
}
