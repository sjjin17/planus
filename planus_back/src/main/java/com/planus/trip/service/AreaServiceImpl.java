package com.planus.trip.service;

import com.planus.db.entity.Area;
import com.planus.db.entity.TripArea;
import com.planus.db.repository.AreaRepository;
import com.planus.db.repository.TripAreaRepository;
import com.planus.trip.dto.AreaResDTO;
import com.planus.trip.dto.BestTripAreaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AreaServiceImpl implements AreaService{
    private AreaRepository areaRepository;
    private TripAreaRepository tripAreaRepository;

    @Autowired
    public AreaServiceImpl(AreaRepository areaRepository, TripAreaRepository tripAreaRepository) {
        this.areaRepository = areaRepository;
        this.tripAreaRepository = tripAreaRepository;
    }

    @Override
    public List<AreaResDTO> findAllArea() {
        List<AreaResDTO> areaResDTOList = new ArrayList<>();
        List<Area> areaList = areaRepository.findAll();
        for (Area area : areaList){
            AreaResDTO areaResDTO = AreaResDTO.builder()
                    .areaId(area.getAreaId())
                    .doName(area.getDoName())
                    .siName(area.getSiName())
                    .build();

            areaResDTOList.add(areaResDTO);
        }

        return areaResDTOList;
    }

    @Override
    public List<BestTripAreaDTO> findBestArea() {
        List<BestTripAreaDTO> bestTripAreaDTOList = new ArrayList<>();
        List<TripArea> tripAreaList = tripAreaRepository.findBestTripArea();
        for(TripArea tripArea : tripAreaList){
            BestTripAreaDTO bestTripAreaDTO = BestTripAreaDTO.builder()
                    .areaId(tripArea.getArea().getAreaId())
                    .siName(tripArea.getArea().getSiName())
                    .build();

            bestTripAreaDTOList.add(bestTripAreaDTO);
        }

        return bestTripAreaDTOList;
    }
}
