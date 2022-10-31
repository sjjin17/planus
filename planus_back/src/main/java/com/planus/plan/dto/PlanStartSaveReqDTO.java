package com.planus.plan.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanStartSaveReqDTO {
    long tripId;
    List<PlanListStartReqDTO> planListStartReqDTOList;

    @Builder
    public PlanStartSaveReqDTO(long tripId, List<PlanListStartReqDTO> planListStartReqDTOList) {
        this.tripId = tripId;
        this.planListStartReqDTOList = planListStartReqDTOList;
    }
}
