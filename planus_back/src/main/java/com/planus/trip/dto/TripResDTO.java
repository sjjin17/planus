package com.planus.trip.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TripResDTO {
    private long tripId;
    private String tripUrl;

    public TripResDTO(long tripId, String tripUrl) {
        this.tripId = tripId;
        this.tripUrl = tripUrl;
    }
}
