package com.planus.plan.service;

import com.planus.plan.dto.PlanResDTO;
import com.planus.plan.dto.PlanSaveReqDTO;

import java.util.List;

public interface PlanService {
    List<PlanResDTO> readPlan(long tripId);

    long savePlan(PlanSaveReqDTO planSaveReqDTO);
}
