package com.planus.article.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.util.List;

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

    public static ArticleResDTO toDTO(Pageable pageable, List<ArticleListResDTO> articleList) {
        return ArticleResDTO.builder()
                .currentPage(pageable.getPageNumber())
                .totalPage(pageable.getPageSize())
                .articleList(articleList)
                .build();
    }
}
