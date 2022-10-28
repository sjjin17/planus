package com.planus.db.repository;

import com.planus.db.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {
    Trip save(Trip trip);
    Trip findByTripUrl(String tripUrl);
}
