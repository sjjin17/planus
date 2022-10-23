package com.planus.recommend.repository;

import com.planus.entity.Recommend;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendRepository extends JpaRepository<Recommend, Long> {


    @Query(value = "select r from Recommend r where(6371*acos(cos(radians(:lng))*cos(radians(r.lng))*cos(radians(r.lat)-radians(:lat))+sin(radians(:lng))*sin(radians(r.lng))))<2")
    List<Recommend> findRecommendsByLocation(double lat, double lng, Pageable pageable);
}
