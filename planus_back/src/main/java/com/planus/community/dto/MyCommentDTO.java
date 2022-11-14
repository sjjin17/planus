package com.planus.community.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyCommentDTO {
    private long articleId;
    private String title;
    private long commentId;
    private String content;
    private String regDate;

    public MyCommentDTO(long articleId, String title, long commentId, String content, String regDate) {
        this.articleId = articleId;
        this.title = title;
        this.commentId = commentId;
        this.content = content;
        this.regDate = regDate;
    }
}
