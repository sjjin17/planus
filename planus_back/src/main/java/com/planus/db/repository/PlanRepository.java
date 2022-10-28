package com.planus.db.repository;

import com.planus.db.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    Optional<List<Plan>> findByTripTripId(long tripId);
    Plan save(Plan plan);
}
