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
    private String imageUrl;
    private int participants;
    private List<String> areaList;
}
