package com.planus.recommend.service;

import com.planus.db.entity.Recommend;
import com.planus.db.repository.RecommendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendServiceImpl implements RecommendService{

    private final RecommendRepository  recommendRepository;

    @Autowired
    public RecommendServiceImpl(RecommendRepository recommendRepository){
        this.recommendRepository = recommendRepository;
    }

    @Override
    public List<Recommend> findRecommendByLocation(double lat, double lng, Pageable pageable) {
        List<Recommend> recommendList = recommendRepository.findRecommendsByLocation(lat,lng,pageable);
        return recommendList;
    }

    @Override
    public int countPage(double lat, double lng, int size){
        int count = recommendRepository.countPage(lat,lng);
        int countPage = count/size;
        if(count%size!=0){
            countPage+=1;
        }
        return countPage;
    }
}
