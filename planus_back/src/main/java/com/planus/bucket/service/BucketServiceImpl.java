package com.planus.bucket.service;

import com.planus.bucket.dto.BucketReqDTO;
import com.planus.bucket.dto.BucketResDTO;
import com.planus.db.entity.Bucket;
import com.planus.db.entity.Trip;
import com.planus.db.repository.BucketRepository;
import com.planus.db.repository.TripRepository;
import com.planus.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BucketServiceImpl implements BucketService{

    private final BucketRepository bucketRepository;
    private final TripRepository tripRepository;
    private final RedisTemplate redisTemplate;
    private final RedisUtil redisUtil;
    @Override
    @Transactional(readOnly = true)
    public List<BucketResDTO> findAllBuckets(Long tripId) {

        String key = "bucketList::" + tripId;
        // redis에 값이 있으면 redis에서 불러오고 redis에 값이 없으면 db에서 조회하고 redis에 저장
        if (redisUtil.isExists(key)) {
            System.out.println(redisUtil.getListData(key));
            return redisUtil.getListData(key);
        } else {
            List<Bucket> buckets = bucketRepository.findAllByTripTripId(tripId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 여행정보입니다."));
            List<BucketResDTO> bucketList = buckets.stream().map(bucket -> BucketResDTO.toResDTO(bucket)).collect(Collectors.toList());
            System.out.println(bucketList);
            for (BucketResDTO bucketResDTO : bucketList) {
                System.out.println("yes");
                System.out.println(redisUtil.setListData(key, bucketResDTO));
                System.out.println("hello");

            }
            return bucketList;
        }



    }

    @Override
    @CacheEvict(cacheNames = "bucketList", key="#tripId")
    public Long createBucketList(long tripId) {
        // 해당 tripId의 버킷리스트 db에서 지워주고 -> redis에서 값 가져와서 db에 저장해주고 redis는 비워주기
        String key = "bucketList::" + tripId;
        bucketRepository.deleteAllByTripTripId(tripId);
        Trip trip = tripRepository.findByTripId(tripId);
        List<Bucket> buckets = redisUtil.getListData(key);
        for (Bucket bucket : buckets) {
            bucket.setTrip(trip);
            bucketRepository.save(bucket);
        }

        return tripId;


    }


}
