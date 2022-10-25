package com.planus.db.repository;

import com.planus.db.entity.TripArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripAreaRepository extends JpaRepository<TripArea, Long> {
    TripArea save(TripArea tripArea);
    List<TripArea> findByTripTripId(long tripId);
}
