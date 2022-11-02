package com.planus.mytrip.service;

import com.planus.mytrip.dto.MyTripListResDTO;
import org.springframework.data.domain.Pageable;

public interface MyTripService {

    MyTripListResDTO getMadeTripList(String token, Pageable pageable);
    MyTripListResDTO getSharedTripList(String token, Pageable pageable);
}
