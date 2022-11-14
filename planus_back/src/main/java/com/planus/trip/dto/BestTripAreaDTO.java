package com.planus.trip.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BestTripAreaDTO {
    private long areaId;
    private String siName;

    public BestTripAreaDTO(long areaId, String siName) {
        this.areaId = areaId;
        this.siName = siName;
    }
}
