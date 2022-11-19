package com.planus.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Builder
public class Recommend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="recommend_id")
    private long recommendId;

    @Column(name="place")
    private String place;

    @Column(name="address")
    private String address;

    @Column(name="lat")
    private double lat;

    @Column(name="lng")
    private double lng;

    @Column(name="img_url")
    private String imgUrl;

    @Column(name="content_type")
    private String contentType;

    @Builder
    public Recommend(long recommendId,String place, String address, double lat, double lng, String contentType,String imgUrl){
        this.recommendId = recommendId;
        this.address = address;
        this.contentType = contentType;
        this.place = place;
        this.lat = lat;
        this.lng = lng;
        this.imgUrl= imgUrl;
    }

}
