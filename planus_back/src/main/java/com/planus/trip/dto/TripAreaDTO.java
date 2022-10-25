package com.planus.trip.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TripAreaDTO {
    private long areaId;
    private String doName;
    private String siName;
    private double lat;
    private double lng;

    public TripAreaDTO(long areaId, String doName, String siName, double lat, double lng) {
        this.areaId = areaId;
        this.doName = doName;
        this.siName = siName;
        this.lat = lat;
        this.lng = lng;
    }
}
