package com.planus.db.repository;

import com.planus.db.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Article findByArticleId(long articleId);
    Page<Article> findByTitleLike(String title, Pageable pageable);
    @Query(nativeQuery = true,value = "select * from Article  as a where a.title in (:areas)")
    Page<Article> findByArea(@Param("areas") int[] areaId, Pageable pageable);
}
