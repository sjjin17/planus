package com.planus.article.dto;

import com.planus.db.entity.Article;
import com.planus.db.entity.Trip;
import com.planus.db.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ArticleListResDTO {
    private long articleId;
    private String title;
    private LocalDateTime regDate;
    private long hits;
    private User user;
    private Trip trip;



    @Builder
    public ArticleListResDTO(long articleId, String title, LocalDateTime regDate, long hits, User user, Trip trip) {
        this.articleId = articleId;
        this.title = title;
        this.regDate = regDate;
        this.hits = hits;
        this.user = user;
        this.trip = trip;
    }

    // entity -> ResDTO
    public static ArticleListResDTO toResDTO(Article article) {
        return ArticleListResDTO.builder()
                .articleId(article.getArticleId())
                .title(article.getTitle())
                .regDate(article.getRegDate())
                .hits(article.getHits())
                .user(article.getUser())
                .trip(article.getTrip())
                .build();


    }

}
