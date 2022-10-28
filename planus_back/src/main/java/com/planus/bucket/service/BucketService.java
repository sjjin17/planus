package com.planus.bucket.service;

import com.planus.bucket.dto.BucketRedisResDTO;
import com.planus.bucket.dto.BucketReqDTO;
import com.planus.bucket.dto.BucketResDTO;
import com.planus.db.entity.Bucket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface BucketService {
    List<BucketRedisResDTO> findAllBuckets(Long tripId);

    Long createBucketList(long tripId);


}
