package com.planus.entity;

import javax.persistence.*;

@Entity
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="area_id")
    private Area area;

}
