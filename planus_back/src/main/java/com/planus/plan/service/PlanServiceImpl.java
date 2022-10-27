package com.planus.plan.service;

import com.planus.db.entity.Plan;
import com.planus.db.entity.Timetable;
import com.planus.db.repository.PlanRepository;
import com.planus.db.repository.TimetableRepository;
import com.planus.plan.dto.PlanReqDTO;
import com.planus.plan.dto.PlanResDTO;
import com.planus.plan.dto.PlanSaveReqDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {
    private final PlanRepository planRepository;
    private final TimetableRepository timetableRepository;

    public PlanServiceImpl(PlanRepository planRepository, TimetableRepository timetableRepository) {
        this.planRepository = planRepository;
        this.timetableRepository = timetableRepository;
    }

    @Override
    public List<PlanResDTO> readPlan(long tripId) {
        List<PlanResDTO> planResDTOList = new ArrayList<>();

        // 일별일정 목록과 각 일별일정의 일정 목록 찾기
        List<Plan> planList = planRepository.findByTripTripId(tripId).orElseThrow();

        for (Plan p: planList) {
            PlanResDTO planResDTO = PlanResDTO.builder()
                    .planId(p.getPlanId())
                    .tripDate(p.getTripDate())
                    .startTime(p.getStartTime())
                    .timetableList(p.getTimetableList())
                    .build();

            planResDTOList.add(planResDTO);
        }

        return planResDTOList;
    }

    @Override
    public long savePlan(PlanSaveReqDTO planSaveReqDTO) {
        //일별일정 수정(저장)
        //기존의 timetable 삭제
        //새로운 timetable로 저장

        List<Plan> planList = new ArrayList<>();

        for (PlanReqDTO p:planSaveReqDTO.getPlanReqDTOList()) {
            //기존의 timetable list 삭제하고, timetable 테이블에 새로운 리스트를 저장해 줌
            timetableRepository.deleteByPlanPlanId(p.getPlanId());
            timetableRepository.saveAll(p.getTimetableList());

            // 수정된 출발시간 등의 정보로 Plan 객체 생성
            // timetableList가 중복 생성되지는 않는지 테스트 필요
            Plan plan = Plan.builder()
                    .planId(p.getPlanId())
                    .tripDate(p.getTripDate())
                    .startTime(p.getStartTime())
                    .timetableList(p.getTimetableList())
                    .build();

            planList.add(plan);
        }

        //새로 만들어진 Plan 객체 리스트를 저장
        planRepository.save(planList);

        return planSaveReqDTO.getTripId();
    }
}
