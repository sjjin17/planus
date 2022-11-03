package com.planus.mytrip.service;

import com.planus.db.entity.Area;
import com.planus.db.entity.Trip;
import com.planus.db.repository.AreaRepository;
import com.planus.db.repository.MemberRepository;
import com.planus.db.repository.TripRepository;
import com.planus.mytrip.dto.MyTripListResDTO;
import com.planus.mytrip.dto.MyTripResDTO;
import com.planus.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service("myTripService")
public class MyTripServiceImpl implements MyTripService {

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    private final TokenProvider tokenProvider;
    private final TripRepository tripRepository;
    private final MemberRepository memberRepository;
    private final AreaRepository areaRepository;

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
    public String deleteTrip(String token, Long tripId) {
        Long userId = tokenProvider.getUserId(token.split(" ")[1]);
        Trip trip = tripRepository.findByTripId(tripId);
        // 완료된 일정 (참가자에서만 삭제)
        if(trip.isComplete()) {
            memberRepository.deleteByTripIdAndUserId(tripId, userId);
            return SUCCESS;
        }
        // 진행중 일정 - 참가자 (참가자에서만 삭제)
        if(trip.getTripId() != userId) {
            memberRepository.deleteByTripIdAndUserId(tripId, userId);
            return SUCCESS;
        }
        // 진행중 일정 - 방장 - 잔여 참가자 O (참가자에서 삭제, 방장 위임)
        if(memberRepository.countByTripTripId(trip.getTripId()) > 1) {
            // 진행중 일정 - 방장 - 잔여 참가자 X (참가자에서 삭제, 전체 일정 삭제)
            return SUCCESS;
        }
        // 참가자 더 없으면 일정 삭제
        return SUCCESS;

//        return FAIL;
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
