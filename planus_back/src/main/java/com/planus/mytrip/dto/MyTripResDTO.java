package com.planus.mytrip.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MyTripResDTO {
    private Long tripId;
    private String tripUrl;
    private String startDate;
    private String endDate;
    private boolean complete;
    private Long participants;
    private String imageUrl;
    private List<String> areaList;
}
