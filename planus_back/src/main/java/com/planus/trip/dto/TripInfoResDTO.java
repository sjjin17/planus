package com.planus.trip.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TripInfoResDTO {
    private long tripId;
    private long admin;
    private String startDate;
    private long period;
    private int memberOrAdmin;
    private boolean complete;
    private String imageUrl;
    private List<TripAreaDTO> tripArea;

    public TripInfoResDTO(long tripId, long admin, String startDate, long period, int memberOrAdmin, boolean complete, String imageUrl, List<TripAreaDTO> tripArea) {
        this.tripId = tripId;
        this.admin = admin;
        this.startDate = startDate;
        this.period = period;
        this.memberOrAdmin = memberOrAdmin;
        this.complete = complete;
        this.imageUrl = imageUrl;
        this.tripArea = tripArea;
    }
}
