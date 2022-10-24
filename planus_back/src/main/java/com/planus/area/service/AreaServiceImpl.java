package com.planus.area.service;

import com.planus.area.dto.AreaResDTO;
import com.planus.db.entity.Area;
import com.planus.db.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AreaServiceImpl implements AreaService{
    private AreaRepository areaRepository;

    @Autowired
    public AreaServiceImpl(AreaRepository areaRepository){
        this.areaRepository = areaRepository;
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
}
