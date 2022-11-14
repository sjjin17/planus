package com.planus.community.service;

import com.planus.community.dto.CommentResDTO;
import com.planus.community.dto.MyCommentResDTO;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    long addComment(String token, long articleId, String content);
    long modifyComment(String token, long commentId, String content);
    CommentResDTO getArticleComment(long articleId, Pageable pageable);
    MyCommentResDTO getMyComment(String token, Pageable pageable);
    void delComment(String token, long commentId);
}
