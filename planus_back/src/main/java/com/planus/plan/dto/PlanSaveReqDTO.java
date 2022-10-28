package com.planus.plan.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanSaveReqDTO {
    long tripId;
    List<PlanReqDTO> planReqDTOList;

    @Builder
    public PlanSaveReqDTO(long tripId, List<PlanReqDTO> planReqDTOList) {
        this.tripId = tripId;
        this.planReqDTOList = planReqDTOList;
    }
}
