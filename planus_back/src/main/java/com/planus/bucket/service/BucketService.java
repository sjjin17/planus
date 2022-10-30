package com.planus.bucket.service;

import com.planus.bucket.dto.BucketResDTO;

import java.util.List;

public interface BucketService {
    List<BucketResDTO> findAllBuckets(Long tripId);

    Long createBucketList(long tripId);


}
