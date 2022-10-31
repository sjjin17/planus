package com.planus.bucket.service;

import com.planus.bucket.dto.BucketResDTO;
import com.planus.websocket.model.WebSocketBucket;

import java.util.List;

public interface BucketService {
    List<BucketResDTO> findAllBuckets(Long tripId);

    Long createBucketList(long tripId);

    void deleteBucket(long tripId, WebSocketBucket bucket);

    void addBucket(long tripId, WebSocketBucket bucket);
}
