package com.planus.community.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MyCommentResDTO {
    private int currentPage;
    private int totalPage;
    private List<MyCommentDTO> commentList;

    public MyCommentResDTO(int currentPage, int totalPage, List<MyCommentDTO> commentList) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.commentList = commentList;
    }
}
