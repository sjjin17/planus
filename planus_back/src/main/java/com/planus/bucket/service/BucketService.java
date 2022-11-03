package com.planus.bucket.service;

import com.planus.bucket.dto.BucketResDTO;
import com.planus.websocket.model.WebSocketBucket;
import com.planus.websocket.model.WebSocketTimetable;

import java.util.List;

public interface BucketService {
    List<BucketResDTO> findAllBuckets(Long tripId);

    Long createBucketList(long tripId);

    void deleteBucket(WebSocketBucket bucket);

    void addBucket(WebSocketBucket bucket);

    void moveToPlan(WebSocketTimetable timetable);
}
