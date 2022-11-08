package com.planus.community.dto;

import com.planus.db.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CommentResDTO {
    private int currentPage;
    private int totalPage;
    private List<CommentDTO> commentList;

    public CommentResDTO(int currentPage, int totalPage, List<CommentDTO> commentList) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.commentList = commentList;
    }
}
