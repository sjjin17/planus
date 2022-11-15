package com.planus.trip.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FestivalResDTO {
    private int currentPage;
    private int totalPage;
    private List<FestivalDTO> festivalList;

    public FestivalResDTO(int currentPage, int totalPage, List<FestivalDTO> festivalList) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.festivalList = festivalList;
    }
}
