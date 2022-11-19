package com.planus.mytrip.service;

import com.planus.mytrip.dto.MyTripListResDTO;
import com.planus.mytrip.dto.MyTripResDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MyTripService {

    MyTripListResDTO getMadeTripList(String token, Pageable pageable);
    MyTripListResDTO getSharedTripList(String token, Pageable pageable);
    List<MyTripResDTO> getMyTripList(String token);
    String deleteTrip(String token, Long tripId);
}
