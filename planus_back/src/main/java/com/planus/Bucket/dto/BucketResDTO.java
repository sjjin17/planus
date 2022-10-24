package com.planus.Bucket.dto;

import com.planus.db.entity.Bucket;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BucketResDTO {
    private Long bucketId;

    private String place;

    private String address;

    private Double lat;

    private Double lng;


    @Builder
    public BucketResDTO(Long bucketId, String place, String address, Double lat, Double lng) {
        this.bucketId = bucketId;
        this.place = place;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }

    public static BucketResDTO toResDTO(Bucket bucket) {
        return BucketResDTO.builder()
                .bucketId(bucket.getBucketId())
                .place(bucket.getPlace())
                .address(bucket.getAddress())
                .lat(bucket.getLat())
                .lng(bucket.getLng())
                .build();
    }

}
