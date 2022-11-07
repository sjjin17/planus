package com.planus.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class CommentReqDTO {
    private long id;
    private String content;

    public CommentReqDTO(long id, String content) {
        this.id = id;
        this.content = content;
    }
}
