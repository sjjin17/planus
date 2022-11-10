package com.planus.article.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SearchDTO {
    private long articleId;
    private long tripId;
    private List<String> areaList;
    private String imageUrl;
    private long period;
    private long userId;
    private String name;
    private String title;
    private String regDate;
    private long hits;
    private long likes;
    private boolean isLiked;

    public SearchDTO(long articleId, long tripId, List<String> areaList, String imageUrl, long period, long userId, String name, String title, String regDate, long hits, long likes, boolean isLiked) {
        this.articleId = articleId;
        this.tripId = tripId;
        this.areaList = areaList;
        this.imageUrl = imageUrl;
        this.period = period;
        this.userId = userId;
        this.name = name;
        this.title = title;
        this.regDate = regDate;
        this.hits = hits;
        this.likes = likes;
        this.isLiked = isLiked;
    }
}
