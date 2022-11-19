package com.planus.db.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.client.RemoveAuthorizedClientOAuth2AuthorizationFailureHandler;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private long tripId;

    @Column(name = "trip_url")
    private String tripUrl;

    @Column(name = "admin")
    private long admin;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "period")
    private long period;

    @Column(name = "complete")
    private boolean complete;

    @Column(name = "image_url")
    private String imageUrl;

    @JsonManagedReference
    @OneToMany(mappedBy = "trip", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Member> memberList;

    @JsonManagedReference
    @OneToMany(mappedBy = "trip", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Bucket> bucketList;

    @JsonManagedReference
    @OneToMany(mappedBy = "trip", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<TripArea> tripAreaList;

    @JsonManagedReference
    @OneToMany(mappedBy = "trip", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Plan> planList;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.REMOVE)
    private List<Article> articleList;


    @Builder
    public Trip(long tripId, String tripUrl, long admin, LocalDateTime createTime, LocalDate startDate, long period, boolean complete, String imageUrl, List<Member> memberList, List<Bucket> bucketList, List<TripArea> tripAreaList, List<Plan> planList) {
        this.tripId = tripId;
        this.tripUrl = tripUrl;
        this.admin = admin;
        this.createTime = createTime;
        this.startDate = startDate;
        this.period = period;
        this.complete = complete;
        this.imageUrl = imageUrl;
        this.memberList = memberList;
        this.bucketList = bucketList;
        this.tripAreaList = tripAreaList;
        this.planList = planList;
    }

    public void changeAdmin(long userId) {
        this.admin = userId;
    }

    public void uploadImage(String imgUrl) {
        this.imageUrl = imgUrl;
    }
}
