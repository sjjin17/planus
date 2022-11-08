package com.planus.community.service;

import com.planus.community.dto.CommentResDTO;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    long addComment(String token, long articleId, String content);
    long modifyComment(String token, long commentId, String content);
    CommentResDTO getArticleComment(long articleId, Pageable pageable);
    CommentResDTO getMyComment(String token, Pageable pageable);
    void delComment(String token, long commentId);
}
