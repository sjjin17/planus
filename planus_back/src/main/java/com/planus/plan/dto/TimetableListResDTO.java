package com.planus.plan.dto;

import com.planus.db.entity.Transit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TimetableListResDTO {
    long timetableId;
    int orders;
    String place;
    double lat;
    double lng;
    int costTime;
    Transit transit;
    int moveTime;
    String moveRoute;

    @Builder

    public TimetableListResDTO(long timetableId, int orders, String place, double lat, double lng, int costTime, Transit transit, int moveTime, String moveRoute) {
        this.timetableId = timetableId;
        this.orders = orders;
        this.place = place;
        this.lat = lat;
        this.lng = lng;
        this.costTime = costTime;
        this.transit = transit;
        this.moveTime = moveTime;
        this.moveRoute = moveRoute;
    }
}
