package com.planus.article.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SearchResDTO {
    private int currentPage;
    private int totalPage;
    private List<SearchDTO> articleList;

    public SearchResDTO(int currentPage, int totalPage, List<SearchDTO> articleList) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.articleList = articleList;
    }
}
