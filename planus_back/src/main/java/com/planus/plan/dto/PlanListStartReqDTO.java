package com.planus.plan.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanListStartReqDTO {
    long planId;
    LocalDate tripDate;
    int startTime;

    @Builder
    public PlanListStartReqDTO(long planId, LocalDate tripDate, int startTime) {
        this.planId = planId;
        this.tripDate = tripDate;
        this.startTime = startTime;
    }
}
