package com.planus.article.dto;

import com.planus.db.entity.Article;
import com.planus.db.entity.Trip;
import com.planus.db.entity.User;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@ToString
public class ArticleReqDTO {

    @NotBlank @Size(max=255)
    private String title;

    private String content;
    private Long tripId;



    // articleReqDTO -> article
    public static Article toEntity(ArticleReqDTO articleReqDTO, Trip trip, User user) {
        return Article.builder()
                .title(articleReqDTO.getTitle())
                .content(articleReqDTO.getContent())
                .trip(trip)
                .user(user)
                .build();

    }

}
