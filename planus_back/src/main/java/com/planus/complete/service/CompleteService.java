package com.planus.complete.service;


import com.planus.complete.dto.CompleteResDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CompleteService {
    CompleteResDTO getCompleteTrip(String uuid);
    String copyTrip(String token, Long tripId, String startDate);

    String completeImageUpload(Long tripId, MultipartFile image) throws IOException;
}
