package com.planus.trip.service;

import com.planus.trip.dto.AreaResDTO;
import com.planus.trip.dto.BestTripAreaDTO;
import com.planus.trip.dto.FestivalResDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AreaService {
    List<AreaResDTO> findAllArea();
    List<BestTripAreaDTO> findBestArea();
    FestivalResDTO findFestival(Pageable pageable);
}
