package com.planus.complete.service;


import com.planus.complete.dto.CompleteResDTO;
import com.planus.db.entity.*;
import com.planus.db.repository.*;
import com.planus.plan.dto.PlanResDTO;
import com.planus.plan.dto.TimetableListResDTO;
import com.planus.util.FileUpload;
import com.planus.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompleteServiceImpl implements CompleteService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final TokenProvider tokenProvider;
    private final TripRepository tripRepository;
    private final TripAreaRepository tripAreaRepository;
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final TimetableRepository timetableRepository;
    private final FileUpload fileUpload;

    private static final String FAIL = "fail";

    @Override
    public CompleteResDTO getCompleteTrip(String uuid) {
        Trip trip = tripRepository.findByTripUrl(uuid);
        CompleteResDTO completeResDTO = tripToCompleteResDTO(trip);
        return completeResDTO;
    }

    @Transactional
    @Override
    public String copyTrip(String token, Long tripId, String startDate) {
        Long userId = tokenProvider.getUserId(token.split(" ")[1]);

        // trip 저장
        Trip completeTrip = tripRepository.findByTripId(tripId);
        Trip newTrip = Trip.builder()
                .tripUrl(UUID.randomUUID().toString())
                .admin(userId)
                .createTime(LocalDateTime.now())
                .startDate(LocalDate.parse(startDate))
                .period(completeTrip.getPeriod())
                .build();
        tripRepository.save(newTrip);

        // tripArea 저장
        List<TripArea> completeTripArea = tripAreaRepository.findByTripTripId(tripId);
        List<TripArea> newTripArea = new ArrayList<>();
        for (TripArea ta : completeTripArea) {
            newTripArea.add(
                    TripArea.builder()
                            .trip(newTrip)
                            .area(ta.getArea())
                            .build()
            );
        }
        tripAreaRepository.saveAll(newTripArea);

        // member 저장
        memberRepository.save(
                Member.builder()
                        .trip(newTrip)
                        .user(userRepository.findByUserId(userId))
                        .build()
        );

        // plan & timetable 저장
        List<Plan> completePlan = planRepository.findByTripTripId(tripId).orElseThrow();
        int plusDay = (int) ChronoUnit.DAYS.between(completeTrip.getStartDate(), LocalDate.parse(startDate));
        for (Plan p : completePlan) {
            Plan newPlan = Plan.builder()
                    .trip(newTrip)
                    .tripDate(p.getTripDate().plusDays(plusDay))
                    .startTime(p.getStartTime())
                    .build();

            planRepository.save(newPlan);

            List<Timetable> completeTimetable = timetableRepository.findByPlanPlanId(p.getPlanId()).orElseThrow();
            List<Timetable> newTimetable = new ArrayList<>();
            for (Timetable tt : completeTimetable) {
                newTimetable.add(
                        Timetable.builder()
                                .orders(tt.getOrders())
                                .place(tt.getPlace())
                                .lat(tt.getLat())
                                .lng(tt.getLng())
                                .costTime(tt.getCostTime())
                                .transit(tt.getTransit())
                                .moveTime(tt.getMoveTime())
                                .moveRoute(tt.getMoveRoute())
                                .plan(newPlan)
                                .build()
                );
            }
            timetableRepository.saveAll(newTimetable);
        }

        return newTrip.getTripUrl();
    }

    @Transactional
    @Override
    public String completeImageUpload(Long tripId, MultipartFile image) throws IOException {
        Trip trip = tripRepository.findByTripId(tripId);
        if(!trip.isComplete())
            return FAIL;

        String imagePath = fileUpload.fileUpload(image);
        trip.uploadImage(imagePath);
        tripRepository.save(trip);
        return imagePath;
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
