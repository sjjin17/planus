package com.planus.trip.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class TripInfoResDTO {
    private long tripId;
    private long admin;
    private LocalDate startDate;
    private int period;
    private List<TripAreaDTO> tripArea;

    public TripInfoResDTO(long tripId, long admin, LocalDate startDate, int period, List<TripAreaDTO> tripArea) {
        this.tripId = tripId;
        this.admin = admin;
        this.startDate = startDate;
        this.period = period;
        this.tripArea = tripArea;
    }
}
