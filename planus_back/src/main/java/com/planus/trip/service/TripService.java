package com.planus.trip.service;

import com.planus.trip.dto.TripInfoResDTO;
import com.planus.trip.dto.TripResDTO;

import java.time.LocalDate;

public interface TripService {
    TripResDTO createTrip(long admin, LocalDate startDate, int period, int[] areaId);
    TripInfoResDTO findTripInfo(long tripId);
}
