package com.planus.trip.service;

import com.planus.db.entity.Trip;
import com.planus.trip.dto.TripInfoResDTO;
import com.planus.trip.dto.TripResDTO;

public interface TripService {
    TripResDTO createTrip(String token, String startDate, long period, int[] areaId);
    TripInfoResDTO findTripInfo(String token, String tripUrl);
    long changeAdmin(String token, long tripId, long userId);
    boolean changeComplete(long tripId, boolean complete);

    Trip findByTripId(long tripId);

    long changeAdminForWebSocket(Trip trip, long userId);
}
