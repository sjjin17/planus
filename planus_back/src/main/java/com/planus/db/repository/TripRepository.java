package com.planus.db.repository;

import com.planus.db.entity.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {
    Trip save(Trip trip);
    Trip findByTripId(long tripId);

    Page<Trip> findByAdmin(long admin);
}
