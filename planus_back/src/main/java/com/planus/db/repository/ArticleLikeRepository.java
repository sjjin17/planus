package com.planus.db.repository;

import com.planus.db.entity.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {
    boolean existsByArticleArticleIdAndUserUserId(long articleId, long userId);
//    int countByArticleArticleIdAndUserUserId(long articleId, long userId);
    void deleteByArticleArticleIdAndUserUserId(long articleId, long userId);
    int countByArticleArticleId(long articleId);



}
