package com.planus.db.repository;

import com.planus.db.entity.TripArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TripAreaRepository extends JpaRepository<TripArea, Long> {
    TripArea save(TripArea tripArea);
    List<TripArea> findByTripTripId(long tripId);
    void deleteAllByTripTripId(long tripId);
    @Query(nativeQuery = true,value = "select ANY_VALUE(triparea_id) as triparea_id,ANY_VALUE(trip_id) as trip_id,ta.area_id,si_name,count(ta.area_id) as count from TripArea ta, Area a where ta.area_id = a.area_id group by area_id order by count desc limit 4")
    List<TripArea> findBestTripArea();
}
