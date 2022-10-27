package com.planus.mytrip.service;

import com.planus.mytrip.dto.MyTripListResDTO;

public interface MyTripService {

    MyTripListResDTO getMadeTripList(String token);
}
