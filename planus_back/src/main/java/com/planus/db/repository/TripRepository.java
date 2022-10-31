package com.planus.db.repository;

import com.planus.db.entity.Trip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {
    Trip save(Trip trip);
    Trip findByTripUrl(String tripUrl);
    Page<Trip> findByAdminOrderByCreateTimeDesc(long admin, Pageable pageable);

    Page<Trip> findAllByAdminNotAndMemberList_User_UserIdOrderByCreateTimeDesc(long admin, long userId, Pageable pageable);
}
