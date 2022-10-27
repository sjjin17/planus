package com.planus.trip.service;

import com.planus.trip.dto.TripInfoResDTO;
import com.planus.trip.dto.TripResDTO;

public interface TripService {
    TripResDTO createTrip(long admin, String startDate, long period, int[] areaId);
    TripInfoResDTO findTripInfo(long tripId);
}
