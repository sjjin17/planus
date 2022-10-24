package com.planus.Bucket.service;

import com.planus.Bucket.dto.BucketResDTO;
import com.planus.db.entity.Bucket;
import com.planus.db.entity.Trip;
import com.planus.db.repository.BucketRepository;
import com.planus.db.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BucketServiceImpl implements BucketService{

    private final BucketRepository bucketRepository;
    private final TripRepository tripRepository;
    @Override
    @Transactional(readOnly = true)
    public List<BucketResDTO> findAllBuckets(Long tripId) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 여행정보입니다."));
        List<Bucket> buckets = bucketRepository.findAllByTrip(trip).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 여행정보입니다."));
        // redis에 데이터가 남아 있지 않다면 mysql에 저장되어 있는 버킷리스트만 조회

        return buckets.stream().map(bucket -> BucketResDTO.toResDTO(bucket)).collect(Collectors.toList());


    }
}
