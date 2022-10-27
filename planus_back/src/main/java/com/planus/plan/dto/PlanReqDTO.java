package com.planus.plan.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanReqDTO {
    long planId;
    LocalDate tripDate;
    int startTime;
    List<TimetableReqDTO> timetableReqDTOList;

    @Builder
    public PlanReqDTO(long planId, LocalDate tripDate, int startTime, List<TimetableReqDTO> timetableReqDTOList) {
        this.planId = planId;
        this.tripDate = tripDate;
        this.startTime = startTime;
        this.timetableReqDTOList = timetableReqDTOList;
    }
}
