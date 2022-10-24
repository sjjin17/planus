package com.planus.Bucket.controller;

import com.planus.Bucket.service.BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/buckets")
public class BucketApiController {

    private final BucketService bucketService;

    @GetMapping("/{tripId}")
    public ResponseEntity getAllbuckets(@PathVariable Long tripId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(bucketService.findAllBuckets(tripId));
    }
}
