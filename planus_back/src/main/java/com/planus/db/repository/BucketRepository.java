package com.planus.db.repository;

import com.planus.db.entity.Bucket;
import com.planus.db.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BucketRepository extends JpaRepository<Bucket, Long> {
    Optional<List<Bucket>> findAllByTrip(Trip trip);
}
