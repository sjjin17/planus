package com.planus.plan.dto;

import com.planus.db.entity.Plan;
import com.planus.db.entity.Timetable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanResDTO {
    long planId;
    LocalDate tripDate;
    int startTime;
    List<TimetableListResDTO> timetableList;

    @Builder
    public PlanResDTO(long planId, LocalDate tripDate, int startTime, List<TimetableListResDTO> timetableList) {
        this.planId = planId;
        this.tripDate = tripDate;
        this.startTime = startTime;
        this.timetableList = timetableList;
    }

    public static PlanResDTO toResDTO(Plan plan) {
        return PlanResDTO.builder()
                .planId(plan.getPlanId())
                .tripDate(plan.getTripDate())
                .startTime(plan.getStartTime())
                .timetableList(plan.getTimetableList().stream().map(timetable -> TimetableListResDTO.toResDTO(timetable)).collect(Collectors.toList()))
                .build();

    }


}
