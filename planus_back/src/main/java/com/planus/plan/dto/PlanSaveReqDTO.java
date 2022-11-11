package com.planus.plan.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanSaveReqDTO {
    List<Long> planIdList;
    boolean complete;

    @Builder
    public PlanSaveReqDTO(List<Long> planIdList, boolean complete) {
        this.planIdList = planIdList;
        this.complete = complete;
    }
}
