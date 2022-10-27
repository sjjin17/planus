package com.planus.trip.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
public class TripReqDTO {
    private LocalDate startDate;
    private int period;
    private int[] areaId;
    private int admin;

    public TripReqDTO(LocalDate startDate, int period, int[] areaId, int admin) {
        this.startDate = startDate;
        this.period = period;
        this.areaId = areaId;
        this.admin = admin;
    }
}
