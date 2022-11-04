package com.planus.complete.service;


import com.planus.complete.dto.CompleteResDTO;
import com.planus.db.entity.Plan;
import com.planus.db.entity.Timetable;
import com.planus.db.entity.Trip;
import com.planus.db.repository.TripRepository;
import com.planus.plan.dto.PlanResDTO;
import com.planus.plan.dto.TimetableListResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompleteServiceImpl implements CompleteService {

    private final TripRepository tripRepository;

    @Override
    public CompleteResDTO getCompleteTrip(String uuid) {
        Trip trip = tripRepository.findByTripUrl(uuid);
        CompleteResDTO completeResDTO = tripToCompleteResDTO(trip);
        return completeResDTO;
    }

    public CompleteResDTO tripToCompleteResDTO(Trip trip) {

        List<PlanResDTO> planResDTOList = new ArrayList<>();
        for (Plan plan : trip.getPlanList()) {
            planResDTOList.add(planToPlanResDTO(plan));
        }

        return CompleteResDTO.builder()
                .startDate(trip.getStartDate())
                .planResDTOList(planResDTOList)
                .build();

    }

    public PlanResDTO planToPlanResDTO(Plan plan) {
        List<TimetableListResDTO> timeTableDTOList = getTimeTableResDTOList(plan.getTimetableList());

        return PlanResDTO.builder()
                .planId(plan.getPlanId())
                .tripDate(plan.getTripDate())
                .startTime(plan.getStartTime())
                .timetableList(timeTableDTOList)
                .build();
    }

    public List<TimetableListResDTO> getTimeTableResDTOList(List<Timetable> timetableList) {
        List<TimetableListResDTO> timeTableDTOList = new ArrayList<>();

        for (Timetable timetable : timetableList) {
            timeTableDTOList.add(timeTableToDTO(timetable));
        }
        return timeTableDTOList;
    }

    public TimetableListResDTO timeTableToDTO(Timetable timetable) {
        return TimetableListResDTO.builder()
                .timetableId(timetable.getTimetableId())
                .orders(timetable.getOrders())
                .place(timetable.getPlace())
                .lat(timetable.getLat())
                .lng(timetable.getLng())
                .costTime(timetable.getCostTime())
                .transit(timetable.getTransit())
                .moveTime(timetable.getMoveTime())
                .build();
    }


}
