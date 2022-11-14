package com.planus.trip.service;

import com.planus.db.entity.Festival;
import com.planus.trip.dto.AreaResDTO;
import com.planus.trip.dto.BestTripAreaDTO;

import java.util.List;

public interface AreaService {
    List<AreaResDTO> findAllArea();
    List<BestTripAreaDTO> findBestArea();
    List<Festival> findFestival();
}
