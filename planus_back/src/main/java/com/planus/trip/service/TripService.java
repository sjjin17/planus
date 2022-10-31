package com.planus.trip.service;

import com.planus.trip.dto.TripInfoResDTO;
import com.planus.trip.dto.TripResDTO;

public interface TripService {
    TripResDTO createTrip(String token, String startDate, long period, int[] areaId);
    TripInfoResDTO findTripInfo(String token, String tripUrl);
}
