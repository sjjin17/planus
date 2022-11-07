package com.planus.community.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentReqDTO {
    private long id;
    private String content;

    public CommentReqDTO(long id, String content) {
        this.id = id;
        this.content = content;
    }
}
