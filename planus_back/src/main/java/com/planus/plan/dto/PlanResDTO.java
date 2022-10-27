package com.planus.plan.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanResDTO {
    long planId;
    LocalDate tripDate;
    int startTime;
    List<TimetableResDTO> timetableResDTOList;

    @Builder
    public PlanResDTO(long planId, LocalDate tripDate, int startTime, List<TimetableResDTO> timetableResDTOList) {
        this.planId = planId;
        this.tripDate = tripDate;
        this.startTime = startTime;
        this.timetableResDTOList = timetableResDTOList;
    }
}
