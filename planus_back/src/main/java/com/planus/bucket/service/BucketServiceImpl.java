package com.planus.bucket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planus.bucket.dto.BucketResDTO;
import com.planus.db.entity.Bucket;
import com.planus.db.entity.Trip;
import com.planus.db.repository.BucketRepository;
import com.planus.db.repository.TripRepository;
import com.planus.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BucketServiceImpl implements BucketService{

    private final BucketRepository bucketRepository;
    private final TripRepository tripRepository;
    private final RedisTemplate<String, BucketResDTO> redisTemplate;
    private final RedisUtil redisUtil;
    private final ObjectMapper objectMapper;

    /**
     * redis에 값이 있으면 redis에서 조회
     * redis에 값이 없으면 mysql에서 조회하고 redis에 값 저장
     */

    @Override
    @Transactional(readOnly = true)
    public List<BucketResDTO> findAllBuckets(Long tripId) {
        ZSetOperations<String, BucketResDTO> zSetOperations = redisTemplate.opsForZSet();
        String key = "bucketList::" + tripId;
        if (redisUtil.isExists(key)) {
            Set<BucketResDTO> resultSet = zSetOperations.range(key, 0, -1);
            ArrayList<BucketResDTO> bucketList = new ArrayList<>(resultSet);
            return bucketList;
        } else {
            List<Bucket> buckets = bucketRepository.findAllByTripTripId(tripId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 여행정보입니다."));
            List<BucketResDTO> bucketList = buckets.stream().map(bucket -> BucketResDTO.toResDTO(bucket)).collect(Collectors.toList());
            for (BucketResDTO bucketResDTO : bucketList) {
                zSetOperations.add(key, bucketResDTO, new java.util.Date().getTime());
            }
            return bucketList;
        }

    }


    /**
     * redis에서 data 가져와서 MySQL에 저장
     */
    @Override
    @CacheEvict(cacheNames = "bucketList", key="#tripId")
    public Long createBucketList(long tripId) {
        ZSetOperations<String, BucketResDTO> zSetOperations = redisTemplate.opsForZSet();
        bucketRepository.deleteAllByTripTripId(tripId);
        String key = "bucketList::" + tripId;
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 여행정보입니다."));
        Set<BucketResDTO> buckets = zSetOperations.range(key, 0, -1);
        ArrayList<BucketResDTO> bucketList = new ArrayList<>(buckets);
        for (BucketResDTO bucketResDTO : bucketList) {
            Bucket bucket = BucketResDTO.toEntity(bucketResDTO);
            bucket.setTrip(trip);
            bucketRepository.save(bucket);
        }
        return tripId;
    }


}
