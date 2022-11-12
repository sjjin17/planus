package com.planus.db.repository;

import com.planus.db.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Article findByArticleId(long articleId);
    Page<Article> findAllByOrderByArticleIdDesc(Pageable pageable);
    Page<Article> findByTitleContains(String title, Pageable pageable);
    @Query(nativeQuery = true,value = "select distinct ac.* from Article ac, TripArea ta where ac.trip_id = ta.trip_id and ta.area_id in (:areas)")
    List<Article> findByArea(@Param("areas") int[] area, Pageable pageable);
    @Query(nativeQuery = true,value = "select count(distinct ac.article_id) from Article ac, TripArea ta where ac.trip_id = ta.trip_id and ta.area_id in (:areas)")
    int countPage(@Param("areas") int[] area);

    Page<Article> findByUserUserIdOrderByRegDateDesc(long userId, Pageable pageable);
}
