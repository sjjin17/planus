package com.planus.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@ToString
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="article_id")
    private long articleId;

    @Column(length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @CreatedDate @Column(name="reg_date")
    private LocalDateTime regDate;

    private long hits;

    @OneToMany(mappedBy = "article",cascade = CascadeType.ALL)
    private List<ArticleLike> articleLikeList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @JsonBackReference
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="trip_id")
    @JsonBackReference
    private Trip trip;

    @OneToMany(mappedBy = "article")
    private List<Comment> commentList;


    @Builder
    public Article(String title, String content, User user, Trip trip) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.trip = trip;
        this.hits = 0;
    }


    public void updateArticle(String title, String content, Trip trip, LocalDateTime regDate) {
        this.title = title;
        this.content = content;
        this.trip = trip;
        this.regDate = regDate;
    }

    public void addHits() {
        this.hits += 1;
    }
}
