package com.planus.mytrip.service;

import com.planus.db.entity.Member;
import com.planus.db.entity.Trip;
import com.planus.db.repository.MemberRepository;
import com.planus.db.repository.TripRepository;
import com.planus.login.util.JwtUtil;
import com.planus.mytrip.dto.MyTripListResDTO;
import com.planus.mytrip.dto.MyTripResDTO;
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

    private final JwtUtil jwtUtil;

    private final TripRepository tripRepository;

    private final MemberRepository memberRepository;

    @Override
    public MyTripListResDTO getMadeTripList(String token, Pageable pageable) {
        Page<Trip> madeList = tripRepository.findByAdminOrderByCreateTimeDesc(jwtUtil.getUserIdFromToken(token));
        List<MyTripResDTO> madeTrip = new ArrayList<>();

        for (Trip ml: madeList
             ) {
            madeTrip.add(
                    MyTripResDTO.builder()
                            .tripId(ml.getTripId())
                            .tripUrl(ml.getTripUrl())
                            .startDate(ml.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                            .endDate(ml.getStartDate().plusDays(ml.getPeriod()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                            .complete(ml.isComplete())
                            .participants(memberRepository.countByTripId(ml.getTripId()))
//                            .imageUrl()
//                            .areaList()
                            .build()
            );
        }

        return MyTripListResDTO.builder()
                .currentPage(madeList.getNumber())
                .totalPage(madeList.getTotalPages())
                .tripList(madeTrip)
                .build();
    }
}
