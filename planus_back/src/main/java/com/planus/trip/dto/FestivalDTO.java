package com.planus.trip.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FestivalDTO {
    private String title;
    private String address;
    private String imageUrl;
    private String startDate;
    private String endDate;
    private long areaId;
    private String siName;

    public FestivalDTO(String title, String address, String imageUrl, String startDate, String endDate, long areaId, String siName) {
        this.title = title;
        this.address = address;
        this.imageUrl = imageUrl;
        this.startDate = startDate;
        this.endDate = endDate;
        this.areaId = areaId;
        this.siName = siName;
    }
}
