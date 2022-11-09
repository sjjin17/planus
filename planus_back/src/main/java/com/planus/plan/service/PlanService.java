package com.planus.plan.service;

import com.planus.plan.dto.PlanIdResDTO;
import com.planus.plan.dto.PlanResDTO;
import com.planus.websocket.model.WebSocketPlan;
import com.planus.websocket.model.WebSocketTimetable;
import com.planus.websocket.model.WebSocketTimetableList;

import java.util.List;

public interface PlanService {
    List<PlanIdResDTO> readPlanId(long tripId);
    List<PlanResDTO> readPlanList(List<Long> planIdList);
    void savePlan(long planId, boolean complete);
    void saveTimetable(long planId, boolean complete);

    // ws methods
    void setPlan(long planId, WebSocketPlan plan);
    void addTimetable(long planId, WebSocketTimetable timetable);
    void setTimetableOrders(long planId, WebSocketTimetableList timetableList);
    void setTimetable(long planId, WebSocketTimetable timetable);
    void delTimetable(long planId, WebSocketTimetableList timetableList);
}
