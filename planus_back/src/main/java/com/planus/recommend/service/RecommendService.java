package com.planus.recommend.service;

import com.planus.db.entity.Recommend;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecommendService {
    List<Recommend> findRecommendByLocation(double lat, double lng, Pageable pageable);
}
