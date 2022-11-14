package com.planus.db.repository;

import com.planus.db.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment save(Comment comment);
    Comment findByCommentId(long commentId);
    Page<Comment> findByArticleArticleIdOrderByCommentIdDesc(long articleId, Pageable pageable);
    Page<Comment> findByUserUserIdOrderByCommentIdDesc(long userId, Pageable pageable);
    void deleteByCommentId(long commentId);
}
