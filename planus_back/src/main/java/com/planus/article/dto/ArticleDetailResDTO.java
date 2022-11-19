package com.planus.article.dto;

import com.planus.db.entity.Article;
import com.planus.mytrip.dto.MyTripResDTO;
import com.planus.plan.dto.PlanResDTO;
import com.planus.user.dto.UserResDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class ArticleDetailResDTO {
    private long articleId;
    private String title;
    private String content;
    private String regDate;
    private long hits;
    private long likeCount;
    private boolean isLike;
    private UserResDTO user;
    private MyTripResDTO trip;
    private List<PlanResDTO> planList;

    @Builder
    public ArticleDetailResDTO(long articleId, String title, String content, String regDate, long hits, long likeCount, boolean isLike, UserResDTO user, MyTripResDTO trip, List<PlanResDTO> planList) {
        this.articleId = articleId;
        this.title = title;
        this.content = content;
        this.regDate = regDate;
        this.hits = hits;
        this.likeCount = likeCount;
        this.isLike = isLike;
        this.user = user;
        this.trip = trip;
        this.planList = planList;
    }


    public static ArticleDetailResDTO toEntity(Article article, long likeCount, boolean isLike, UserResDTO user, MyTripResDTO trip, List<PlanResDTO> planList) {
        return ArticleDetailResDTO.builder()
                .articleId(article.getArticleId())
                .title(article.getTitle())
                .content(article.getContent())
                .regDate(article.getRegDate().toString())
                .hits(article.getHits())
                .likeCount(likeCount)
                .isLike(isLike)
                .user(user)
                .trip(trip)
                .planList(planList)
                .build();
    }

}
