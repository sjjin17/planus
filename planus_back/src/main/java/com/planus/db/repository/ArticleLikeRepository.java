package com.planus.db.repository;

import com.planus.db.entity.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {
    boolean existsByArticleArticleIdAndUserUserId(long articleId, long userId);
//    int countByArticleArticleIdAndUserUserId(long articleId, long userId);

}
