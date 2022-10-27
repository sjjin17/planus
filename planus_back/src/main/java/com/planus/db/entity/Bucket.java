package com.planus.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@ToString(exclude="trip")
public class Bucket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bucket_id")
    private Long bucketId;

    @Column(name="place")
    private String place;

    @Column(name="address")
    private String address;

    @Column(name="lat")
    private double lat;

    @Column(name="lng")
    private double lng;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="trip_id")
    private Trip trip;


    //== 연관관계 편의 메서드 ==//
    public void setTrip(Trip trip) {
        this.trip = trip;
        trip.getBucketList().add(this);
    }

    @Builder
    public Bucket(String place, String address, Double lat, Double lng) {
        this.place = place;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }
}
