package com.planus.plan.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanIdResDTO {
    long planId;
    LocalDate tripDate;

    @Builder
    public PlanIdResDTO(long planId, LocalDate tripDate) {
        this.planId = planId;
        this.tripDate = tripDate;
    }
}
