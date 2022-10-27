package com.planus.bucket.service;

import com.planus.bucket.dto.BucketReqDTO;
import com.planus.bucket.dto.BucketResDTO;
import com.planus.db.entity.Bucket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BucketService {
    List<BucketResDTO> findAllBuckets(Long tripId);


}
