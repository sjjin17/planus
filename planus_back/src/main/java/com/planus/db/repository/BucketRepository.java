package com.planus.db.repository;

import com.planus.db.entity.Bucket;
import com.planus.db.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BucketRepository extends JpaRepository<Bucket, Long> {
    Optional<List<Bucket>> findAllByTripTripId(Long TripId);

    void deleteAllByTripTripId(Long TripId);
}
