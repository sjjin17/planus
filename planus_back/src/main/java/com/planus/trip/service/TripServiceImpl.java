package com.planus.trip.service;

import com.planus.db.entity.*;
import com.planus.db.repository.*;
import com.planus.trip.dto.TripAreaDTO;
import com.planus.trip.dto.TripInfoResDTO;
import com.planus.trip.dto.TripResDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TripServiceImpl implements TripService{
    private static final Logger logger = LoggerFactory.getLogger(TripServiceImpl.class);
    private TripRepository tripRepository;
    private AreaRepository areaRepository;
    private TripAreaRepository tripAreaRepository;
    private MemberRepository memberRepository;
    private UserRepository userRepository;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository, AreaRepository areaRepository, TripAreaRepository tripAreaRepository, MemberRepository memberRepository, UserRepository userRepository){
        this.tripRepository = tripRepository;
        this.areaRepository = areaRepository;
        this.tripAreaRepository = tripAreaRepository;
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TripResDTO createTrip(long admin, String startDate, long period, int[] areaId) {
        Trip trip = Trip.builder()
                .admin(admin)
                .createTime(LocalDateTime.now())
                .startDate(LocalDate.parse(startDate))
                .period(period)
                .tripUrl(UUID.randomUUID().toString())
                .build();

        tripRepository.save(trip);

        for (int i=0; i<areaId.length; i++){
            Area area = areaRepository.findByAreaId(areaId[i]);
            TripArea tripArea = TripArea.builder()
                    .trip(trip)
                    .area(area)
                    .build();

            tripAreaRepository.save(tripArea);
        }

        User user = userRepository.findByUserId(admin);

        Member member = Member.builder()
                .trip(trip)
                .user(user)
                .build();

        memberRepository.save(member);

        TripResDTO tripResDTO = TripResDTO.builder()
                .tripId(trip.getTripId())
                .tripUrl(trip.getTripUrl())
                .build();

        return tripResDTO;
    }

    @Override
    public TripInfoResDTO findTripInfo(String tripUrl) {
        Trip trip = tripRepository.findByTripUrl(tripUrl);
        List<TripAreaDTO> tripAreaDTOList = new ArrayList<>();
        List<TripArea> tripAreaList = tripAreaRepository.findByTripTripId(trip.getTripId());

        for (TripArea tripArea : tripAreaList){
            Area area = areaRepository.findByAreaId(tripArea.getArea().getAreaId());
            TripAreaDTO tripAreaDTO = TripAreaDTO.builder()
                    .areaId(area.getAreaId())
                    .doName(area.getDoName())
                    .siName(area.getSiName())
                    .lat(area.getLat())
                    .lng(area.getLng())
                    .build();

            tripAreaDTOList.add(tripAreaDTO);
        }

        TripInfoResDTO tripInfoResDTO = TripInfoResDTO.builder()
                .tripId(trip.getTripId())
                .admin(trip.getAdmin())
                .startDate(trip.getStartDate().toString())
                .period(trip.getPeriod())
                .complete(trip.isComplete())
                .imageUrl(trip.getImageUrl())
                .tripArea(tripAreaDTOList)
                .build();

        return tripInfoResDTO;
    }
}
