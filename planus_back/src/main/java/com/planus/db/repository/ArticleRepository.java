package com.planus.db.repository;

import com.planus.db.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Article findByArticleId(long articleId);

    Page<Article> findAllByOrderByRegDateDesc(Pageable pageable);
    Page<Article> findByTitleContains(String title, Pageable pageable);
    @Query(nativeQuery = true,value = "select distinct ac.* from Article ac, Area a, TripArea ta where ac.trip_id = ta.trip_id and ta.area_id = a.area_id and a.area_id in (:areas)")
    Page<Article> findByArea(@Param("areas") int[] area, Pageable pageable);
}
