package com.planus.bucket.dto;

import com.planus.db.entity.Bucket;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BucketReqDTO {
    private String place;

    private String address;

    private Double lat;

    private Double lng;


    @Builder
    public BucketReqDTO(String place, String address, Double lat, Double lng) {
        this.place = place;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }

    // dto -> entity
    public static Bucket toEntity(BucketReqDTO bucketReqDTO) {
        return Bucket.builder()
                .place(bucketReqDTO.getPlace())
                .address(bucketReqDTO.getAddress())
                .lat(bucketReqDTO.getLat())
                .lng(bucketReqDTO.getLng())
                .build();
    }
}
