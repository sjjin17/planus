package com.planus.db.repository;

import com.planus.db.entity.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {
    Trip save(Trip trip);
    Trip findByTripId(long tripId);
    Trip findByTripUrl(String tripUrl);
    Page<Trip> findByAdminOrderByCreateTimeDesc(long admin, Pageable pageable);
}
