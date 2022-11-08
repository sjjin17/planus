package com.planus.article.service;

import com.planus.article.dto.ArticleListResDTO;
import com.planus.article.dto.ArticleReqDTO;
import com.planus.article.dto.ArticleResDTO;
import com.planus.article.dto.SearchResDTO;
import com.planus.db.entity.Article;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {

    List<ArticleListResDTO> findAllArticles();

    long createArticle(ArticleReqDTO articleReqDTO);

    //ArticleResDTO findOneArticle(long articleId);

    SearchResDTO getArticleListByTitle(String title, Pageable pageable);
    SearchResDTO getArticleListByArea(int[] area, Pageable pageable);
}
