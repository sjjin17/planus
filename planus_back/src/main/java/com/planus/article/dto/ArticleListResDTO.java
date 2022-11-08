package com.planus.article.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.planus.db.entity.Article;
import com.planus.db.entity.Trip;
import com.planus.db.entity.User;
import com.planus.mytrip.dto.MyTripResDTO;
import com.planus.trip.dto.TripInfoResDTO;
import com.planus.user.dto.UserResDTO;
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

    private UserResDTO user;
    private MyTripResDTO trip;



    @Builder
    public ArticleListResDTO(long articleId, String title, LocalDateTime regDate, long hits, UserResDTO user, MyTripResDTO trip) {
        this.articleId = articleId;
        this.title = title;
        this.regDate = regDate;
        this.hits = hits;
        this.user = user;
        this.trip = trip;
    }

    // entity -> ResDTO
    public static ArticleListResDTO toResDTO(Article article) {
        System.out.println(UserResDTO.toResDto(article.getUser()));
        return ArticleListResDTO.builder()
                .articleId(article.getArticleId())
                .title(article.getTitle())
                .regDate(article.getRegDate())
                .hits(article.getHits())
                .user(UserResDTO.toResDto(article.getUser()))
                .trip(MyTripResDTO.toResDTO(article.getTrip()))
                .build();


    }

}
