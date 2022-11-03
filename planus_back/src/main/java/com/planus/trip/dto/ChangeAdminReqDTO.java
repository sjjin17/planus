package com.planus.trip.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChangeAdminReqDTO {
    private long userId;
    private long tripId;

    public ChangeAdminReqDTO(long userId, long tripId) {
        this.userId = userId;
        this.tripId = tripId;
    }
}
