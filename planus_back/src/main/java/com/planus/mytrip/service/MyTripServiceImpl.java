package com.planus.mytrip.service;

import com.planus.mytrip.dto.MyTripListResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("myTripService")
public class MyTripServiceImpl implements MyTripService {
    @Override
    public MyTripListResDTO getMadeTripList(String token) {
        return null;
    }
}
