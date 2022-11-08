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
    private User user;
    private Trip trip;



    // articleReqDTO -> article
    public static Article toEntity(ArticleReqDTO articleReqDTO) {
        return Article.builder()
                .title(articleReqDTO.getTitle())
                .content(articleReqDTO.getContent())
                .user(articleReqDTO.getUser())
                .trip(articleReqDTO.getTrip())
                .build();

    }

}
