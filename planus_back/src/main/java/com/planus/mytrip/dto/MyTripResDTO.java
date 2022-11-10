package com.planus.mytrip.dto;

import com.planus.db.entity.Trip;
import com.planus.trip.dto.TripResDTO;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class MyTripResDTO {
    private Long tripId;
    private String tripUrl;
    private String startDate;
    private String endDate;
    private boolean complete;
    private Long participants;
    private String imageUrl;
    private List<String> areaList;


    // entity -> ResDTO
    public static MyTripResDTO toResDTO(Trip trip) {

        return MyTripResDTO.builder()
                .tripId(trip.getTripId())
                .tripUrl(trip.getTripUrl())
                .startDate(trip.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .endDate(trip.getStartDate().plusDays(trip.getPeriod()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .complete(trip.isComplete())
                .participants(Long.valueOf(trip.getMemberList().size()))
                .imageUrl(trip.getImageUrl())
                .areaList(trip.getTripAreaList().stream().map(tripArea -> tripArea.getArea().getSiName()).collect(Collectors.toList()))  // 시 이름 가져오기
                .build();
    }
}
