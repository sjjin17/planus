package com.planus.complete.dto;

import com.planus.plan.dto.PlanResDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;



@NoArgsConstructor
@Getter
public class CompleteResDTO {

    LocalDate startDate;

    List<PlanResDTO> planResDTOList;

    @Builder
    public CompleteResDTO(LocalDate startDate, List<PlanResDTO> planResDTOList) {
        this.startDate = startDate;
        this.planResDTOList = planResDTOList;
    }
}
