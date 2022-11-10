package com.planus.community.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentDTO {
    private long commentId;
    private long userId;
    private String name;
    private String content;
    private String regDate;

    public CommentDTO(long commentId, long userId, String name, String content, String regDate) {
        this.commentId = commentId;
        this.userId = userId;
        this.name = name;
        this.content = content;
        this.regDate = regDate;
    }
}
