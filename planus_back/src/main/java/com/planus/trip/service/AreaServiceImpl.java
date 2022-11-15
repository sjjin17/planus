package com.planus.trip.service;

import com.planus.db.entity.Area;
import com.planus.db.entity.Festival;
import com.planus.db.entity.TripArea;
import com.planus.db.repository.AreaRepository;
import com.planus.db.repository.FestivalRepository;
import com.planus.db.repository.TripAreaRepository;
import com.planus.trip.dto.AreaResDTO;
import com.planus.trip.dto.BestTripAreaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AreaServiceImpl implements AreaService{
    private AreaRepository areaRepository;
    private TripAreaRepository tripAreaRepository;
    private FestivalRepository festivalRepository;

    @Autowired
    public AreaServiceImpl(AreaRepository areaRepository, TripAreaRepository tripAreaRepository, FestivalRepository festivalRepository) {
        this.areaRepository = areaRepository;
        this.tripAreaRepository = tripAreaRepository;
        this.festivalRepository = festivalRepository;
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
                    .imageUrl(tripArea.getArea().getImageUrl())
                    .build();

            bestTripAreaDTOList.add(bestTripAreaDTO);
        }

        return bestTripAreaDTOList;
    }

    @Override
    public List<Festival> findFestival() {
        LocalDate today = LocalDate.now();
        LocalDate monthLater = today.plusMonths(1);
        List<Festival> festivalList = festivalRepository.findAllByEndDateAfterAndStartDateBeforeOrderByEndDate(today, monthLater);

        return festivalList;
    }
}
