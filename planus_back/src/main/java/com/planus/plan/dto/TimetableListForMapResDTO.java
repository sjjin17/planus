package com.planus.plan.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimetableListForMapResDTO {
    long timetableId;
    String place;
    int orders;
    double lat;
    double lng;
    long planId;

    @Builder
    public TimetableListForMapResDTO(long timetableId, String place, int orders, double lat, double lng, long planId) {
        this.timetableId = timetableId;
        this.place = place;
        this.orders = orders;
        this.lat = lat;
        this.lng = lng;
        this.planId = planId;
    }
}
