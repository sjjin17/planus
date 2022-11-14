package com.planus.trip.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BestTripAreaDTO {
    private long areaId;
    private String siName;
    private String imageUrl;

    public BestTripAreaDTO(long areaId, String siName, String imageUrl) {
        this.areaId = areaId;
        this.siName = siName;
        this.imageUrl = imageUrl;
    }
}
