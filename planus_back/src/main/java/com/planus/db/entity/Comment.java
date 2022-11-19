package com.planus.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private long commentId;

    @Column(length = 255)
    private String content;

    @Column(name="reg_date")
    @CreatedDate
    private LocalDateTime regDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name="article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name="user_id")
    private User user;

    @Builder
    public Comment(long commentId, String content, LocalDateTime regDate, Article article, User user) {
        this.commentId = commentId;
        this.content = content;
        this.regDate = regDate;
        this.article = article;
        this.user = user;
    }

    public void updateComment(String content, LocalDateTime regDate){
        this.content = content;
        this.regDate = regDate;
    }
}
