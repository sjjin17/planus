package com.planus.plan.dto;

import com.planus.db.entity.Plan;
import com.planus.db.entity.Timetable;
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
    List<TimetableListResDTO> timetableList;

    @Builder
    public PlanResDTO(long planId, LocalDate tripDate, int startTime, List<TimetableListResDTO> timetableList) {
        this.planId = planId;
        this.tripDate = tripDate;
        this.startTime = startTime;
        this.timetableList = timetableList;
    }


}
