package com.planus.plan.service;

import com.planus.plan.dto.PlanResDTO;

import java.util.List;

public interface PlanService {
    List<PlanResDTO> readPlan(long tripId);
}
