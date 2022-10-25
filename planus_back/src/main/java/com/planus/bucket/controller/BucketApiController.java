package com.planus.bucket.controller;

import com.planus.bucket.dto.BucketReqDTO;
import com.planus.bucket.service.BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/buckets")
public class BucketApiController {

    private final BucketService bucketService;

    @GetMapping("/{tripId}")
    public ResponseEntity getAllBuckets(@PathVariable Long tripId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(bucketService.findAllBuckets(tripId));
    }


}
