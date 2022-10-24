package com.planus.Bucket.service;

import com.planus.Bucket.dto.BucketResDTO;

import java.util.List;

public interface BucketService {
    List<BucketResDTO> findAllBuckets(Long tripId);
}
