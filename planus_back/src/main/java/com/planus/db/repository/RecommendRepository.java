package com.planus.db.repository;

import com.planus.db.entity.Recommend;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendRepository extends JpaRepository<Recommend, Long> {


    @Query(value = "select r from Recommend r where(6371*acos(cos(radians(:lng))*cos(radians(r.lng))*cos(radians(r.lat)-radians(:lat))+sin(radians(:lng))*sin(radians(r.lng))))<2 ORDER BY (6371*acos(cos(radians(:lng))*cos(radians(r.lng))*cos(radians(r.lat)-radians(:lat))+sin(radians(:lng))*sin(radians(r.lng))))")
    List<Recommend> findRecommendsByLocation(@Param("lat") double lat,@Param("lng") double lng, Pageable pageable);

    @Query(value = "select count(r) from Recommend r where(6371*acos(cos(radians(:lng))*cos(radians(r.lng))*cos(radians(r.lat)-radians(:lat))+sin(radians(:lng))*sin(radians(r.lng))))<2 ORDER BY (6371*acos(cos(radians(:lng))*cos(radians(r.lng))*cos(radians(r.lat)-radians(:lat))+sin(radians(:lng))*sin(radians(r.lng))))")
    int countPage(@Param("lat") double lat,@Param("lng") double lng);
}
