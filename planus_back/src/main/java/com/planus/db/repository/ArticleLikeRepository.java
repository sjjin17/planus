package com.planus.db.repository;

import com.planus.db.entity.Article;
import com.planus.db.entity.ArticleLike;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {
    boolean existsByArticleArticleIdAndUserUserId(long articleId, long userId);
//    int countByArticleArticleIdAndUserUserId(long articleId, long userId);
    void deleteByArticleArticleIdAndUserUserId(long articleId, long userId);
    int countByArticleArticleId(long articleId);

    int countByUserUserId(long userId);




    //List<ArticleLike> findByUserUserId(Long userId);


}
