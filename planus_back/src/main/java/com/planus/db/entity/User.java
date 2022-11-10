package com.planus.db.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private long userId;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="kakao_id")
    private long kakaoId;

    @Column(name="refresh_token")
    private String refreshToken;

    @Column(name="image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Member> memberList;

    @OneToMany(mappedBy="user", cascade=CascadeType.ALL)
    private List<Article> articleList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> commentList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ArticleLike> articleLikeList;



    @Builder
    public User(long userId, String name, String email, long kakaoId, List<Member> memberList, String refreshToken, String imageUrl) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.kakaoId = kakaoId;
        this.memberList = memberList;
        this.refreshToken = refreshToken;
        this.imageUrl= imageUrl;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

