package com.planus.plan.service;

import com.planus.plan.dto.PlanIdResDTO;
import com.planus.plan.dto.PlanResDTO;

import java.util.List;

public interface PlanService {
    List<PlanIdResDTO> readPlanId(long tripId);
    List<PlanResDTO> readPlanList(List<Long> planIdList);
    void savePlanStart(long planId);
    void savePlan(long planId);
}
