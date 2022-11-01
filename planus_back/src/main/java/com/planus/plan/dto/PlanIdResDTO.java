package com.planus.plan.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanIdResDTO {
    long planId;

    @Builder
    public PlanIdResDTO(long planId) {
        this.planId = planId;
    }
}
