package com.planus.community.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentDTO {
    private long commentId;
    private long articleId;
    private long userId;
    private String content;
    private String regDate;

    public CommentDTO(long commentId, long articleId, long userId, String content, String regDate) {
        this.commentId = commentId;
        this.articleId = articleId;
        this.userId = userId;
        this.content = content;
        this.regDate = regDate;
    }
}
