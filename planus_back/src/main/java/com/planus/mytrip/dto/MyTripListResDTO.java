package com.planus.mytrip.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MyTripListResDTO {
    private int currentPage;
    private int totalPage;
    private List<MyTripResDTO> tripList;
}
