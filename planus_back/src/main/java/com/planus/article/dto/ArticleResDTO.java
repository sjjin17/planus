package com.planus.article.dto;

import com.planus.db.entity.Article;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ArticleResDTO {
    private int currentPage;
    private int totalPage;
    private List<ArticleListResDTO> articleList;

    @Builder
    public ArticleResDTO(int currentPage, int totalPage, List<ArticleListResDTO> articleList) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.articleList = articleList;

    }


    public static ArticleResDTO toDTO(Page<Article> articleList, long likeCount) {
        return ArticleResDTO.builder()
                .currentPage(articleList.getNumber())
                .totalPage(articleList.getTotalPages())
                .articleList(articleList.stream().map(article -> ArticleListResDTO.toResDTO(article, likeCount)).collect(Collectors.toList()))
                .build();
    }
}
