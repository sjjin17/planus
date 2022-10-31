package com.planus.db.repository;

import com.planus.db.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaRepository extends JpaRepository<Area, Long> {
    List<Area> findAll();

    Area findByAreaId(long areaId);

    List<Area> findAllByTripAreaList_Trip_TripId(Long TripId);
}
