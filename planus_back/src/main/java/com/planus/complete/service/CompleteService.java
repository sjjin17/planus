package com.planus.complete.service;


import com.planus.complete.dto.CompleteResDTO;

public interface CompleteService {
    CompleteResDTO getCompleteTrip(String uuid);
    String copyTrip(String token, Long tripId, String startDate);
}
