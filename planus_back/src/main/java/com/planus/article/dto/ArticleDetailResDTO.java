package com.planus.article.dto;

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
    private UserResDTO user;
    private MyTripResDTO trip;
    private List<PlanResDTO> planList;

    @Builder
    public ArticleDetailResDTO(long articleId, String title, String content, String regDate, long hits, UserResDTO user, MyTripResDTO trip, List<PlanResDTO> planList) {
        this.articleId = articleId;
        this.title = title;
        this.content = content;
        this.regDate = regDate;
        this.hits = hits;
        this.user = user;
        this.trip = trip;
        this.planList = planList;
    }

    // entity -> ResDTO
//    public static ArticleResDTO toResDTO(Article article, List<TimetableListResDTO> timetableList) {
//        return ArticleResDTO.builder()
//                .articleId(article.getArticleId())
//                .title(article.getTitle())
//                .content(article.getContent())
//                .regDate(article.getRegDate())
//                .hits(article.getHits())
//                .user(UserResDTO.toResDto(article.getUser()))
//                .trip(MyTripResDTO.toResDTO(article.getTrip()))
//                .planList(article.getTrip().getPlanList().stream().map(plan-> PlanResDTO.toResDTO(plan, timetableList).collect(Collectors.toList())))
//                .build();
//
//    }

}
