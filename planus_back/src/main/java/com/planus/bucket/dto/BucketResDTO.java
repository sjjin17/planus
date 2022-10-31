package com.planus.bucket.dto;

import com.planus.db.entity.Bucket;
import com.planus.websocket.model.WebSocketBucket;
import lombok.*;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@ToString
public class BucketResDTO implements Serializable {


    private String place;

    private String address;

    private Double lat;

    private Double lng;


    @Builder
    public BucketResDTO(String place, String address, Double lat, Double lng) {
        //this.bucketId = bucketId;
        this.place = place;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }

    // Bucket -> BucketResDTO
    public static BucketResDTO toResDTO(Bucket bucket) {
        return BucketResDTO.builder()
                //.bucketId(bucket.getBucketId())
                .place(bucket.getPlace())
                .address(bucket.getAddress())
                .lat(bucket.getLat())
                .lng(bucket.getLng())
                .build();
    }

    // WebSocketBucket -> BucketResDTO
    public static BucketResDTO toResDTO(WebSocketBucket webSocketBucket) {
        return BucketResDTO.builder()
                .place(webSocketBucket.getPlace())
                .address(webSocketBucket.getAddress())
                .lat(webSocketBucket.getLat())
                .lng(webSocketBucket.getLng())
                .build();
    }

    // BucketResDTO -> Bucket
    public static Bucket toEntity(BucketResDTO bucketResDTO) {
        return Bucket.builder()
                .place(bucketResDTO.getPlace())
                .address(bucketResDTO.getAddress())
                .lat(bucketResDTO.getLat())
                .lng(bucketResDTO.getLng())
                .build();
    }





}
