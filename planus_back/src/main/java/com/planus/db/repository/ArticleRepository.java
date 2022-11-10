package com.planus.db.repository;

import com.planus.db.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Article findByArticleId(long articleId);

    Page<Article> findAllByOrderByRegDateDesc(Pageable pageable);
}
