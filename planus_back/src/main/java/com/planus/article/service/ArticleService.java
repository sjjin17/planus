package com.planus.article.service;

import com.planus.article.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {

    List<ArticleListResDTO> findAllArticles();

    long createArticle(ArticleReqDTO articleReqDTO);

    //ArticleResDTO findOneArticle(long articleId);

    SearchResDTO getArticleListByTitle(String token, String title, Pageable pageable);

    List<SearchDTO> getArticleListByArea(String token, int[] area, Pageable pageable);

    int countPage(int[] area);
}
