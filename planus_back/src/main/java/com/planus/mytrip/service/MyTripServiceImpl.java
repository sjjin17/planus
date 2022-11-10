package com.planus.mytrip.service;

import com.planus.db.entity.Area;
import com.planus.db.entity.Trip;
import com.planus.db.repository.*;
import com.planus.mytrip.dto.MyTripListResDTO;
import com.planus.mytrip.dto.MyTripResDTO;
import com.planus.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service("myTripService")
public class MyTripServiceImpl implements MyTripService {

    private static final String SUCCESS = "success";

    private final TokenProvider tokenProvider;
    private final TripRepository tripRepository;
    private final MemberRepository memberRepository;
    private final AreaRepository areaRepository;
    private final TripAreaRepository tripAreaRepository;
    private final BucketRepository bucketRepository;
    private final TimetableRepository timetableRepository;
    private final PlanRepository planRepository;

    @Override
    public MyTripListResDTO getMadeTripList(String token, Pageable pageable) {
        Page<Trip> madeList = tripRepository.findByAdminOrderByCreateTimeDesc(tokenProvider.getUserId(token.split(" ")[1]), pageable);

        return MyTripListResDTO.builder()
                .currentPage(madeList.getNumber())
                .totalPage(madeList.getTotalPages())
                .tripList(setMyTripList(madeList))
                .build();
    }

    @Override
    public MyTripListResDTO getSharedTripList(String token, Pageable pageable) {
        Long userId = tokenProvider.getUserId(token.split(" ")[1]);
        Page<Trip> sharedList = tripRepository.findAllByAdminNotAndMemberList_User_UserIdOrderByCreateTimeDesc(userId, userId, pageable);

        return MyTripListResDTO.builder()
                .currentPage(sharedList.getNumber())
                .totalPage(sharedList.getTotalPages())
                .tripList(setMyTripList(sharedList))
                .build();
    }

    @Override
    public List<MyTripResDTO> getMyTripList(String token) {
        List<Trip> tripList = tripRepository.findAllByMemberList_User_UserIdOrderByCreateTimeDesc(tokenProvider.getUserId(token.split(" ")[1]));
        List<MyTripResDTO> myTripList = new ArrayList<>();

        for (Trip ml: tripList) {
            myTripList.add(
                    MyTripResDTO.builder()
                            .tripId(ml.getTripId())
                            .startDate(ml.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                            .endDate(ml.getStartDate().plusDays(ml.getPeriod()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                            .complete(ml.isComplete())
                            .participants(memberRepository.countByTripTripId(ml.getTripId()))
                            .areaList(areaRepository.findAllByTripAreaList_Trip_TripId(ml.getTripId()).stream().map(Area::getSiName).collect(Collectors.toList()))
                            .build()
            );
        }

        return myTripList;
    }

    @Override
    @Transactional
    public String deleteTrip(String token, Long tripId) {
        Long userId = tokenProvider.getUserId(token.split(" ")[1]);
        Trip trip = tripRepository.findByTripId(tripId);
        //(완료) 완료된 일정 (참가자에서만 삭제, 방장이면 admin = 0)
        if(trip.isComplete()) {
            memberRepository.deleteByTripTripIdAndUserUserId(tripId, userId);
            if(trip.getAdmin() == userId)
                trip.changeAdmin(0);
            return SUCCESS;
        }
        // 진행중 일정 - 참가자 (참가자에서만 삭제)
        if(trip.getAdmin() != userId) {
            memberRepository.deleteByTripTripIdAndUserUserId(tripId, userId);
            return SUCCESS;
        }
        //(완료) 진행중 일정 - 방장 - 잔여 참가자 O (참가자에서 삭제, 방장 위임)
        if(memberRepository.countByTripTripId(trip.getTripId()) > 1) {
            memberRepository.deleteByTripTripIdAndUserUserId(tripId, userId);
            trip.changeAdmin(memberRepository.findTop1ByTripTripId(tripId).getUser().getUserId());
            return SUCCESS;
        }
        // 진행중 일정 - 방장 - 잔여 참가자 X (참가자에서 삭제, 전체 일정 삭제)
        memberRepository.deleteByTripTripIdAndUserUserId(tripId, userId);
        tripAreaRepository.deleteAllByTripTripId(tripId);
        bucketRepository.deleteAllByTripTripId(tripId);
        timetableRepository.deleteAllByPlan_Trip_TripId(tripId);
        planRepository.deleteAllByTripTripId(tripId);
        tripRepository.deleteById(tripId);
        return SUCCESS;
    }

    private List<MyTripResDTO> setMyTripList(Page<Trip> tripList) {
        List<MyTripResDTO> myTripList = new ArrayList<>();

        for (Trip ml: tripList) {
            myTripList.add(
                    MyTripResDTO.builder()
                            .tripId(ml.getTripId())
                            .tripUrl(ml.getTripUrl())
                            .startDate(ml.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                            .endDate(ml.getStartDate().plusDays(ml.getPeriod()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                            .complete(ml.isComplete())
                            .participants(memberRepository.countByTripTripId(ml.getTripId()))
                            .imageUrl(areaRepository.findTop1ByTripAreaList_Trip_TripId(ml.getTripId()).getImageUrl())
                            .areaList(areaRepository.findAllByTripAreaList_Trip_TripId(ml.getTripId()).stream().map(Area::getSiName).collect(Collectors.toList()))
                            .build()
            );
        }

        return myTripList;
    }
}
