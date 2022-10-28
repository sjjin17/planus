package com.planus.bucket.controller;

import com.planus.bucket.dto.BucketReqDTO;
import com.planus.bucket.service.BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/buckets")
public class BucketApiController {

    private final BucketService bucketService;

    @GetMapping("/{tripId}")
    public ResponseEntity getAllBuckets(@PathVariable Long tripId) {
        System.out.println(1);
        return ResponseEntity.status(HttpStatus.OK)
                .body(bucketService.findAllBuckets(tripId));
    }

    @PostMapping("/{tripId}")
    public ResponseEntity createBucketList(@PathVariable Long tripId, @RequestBody List<BucketReqDTO> bucketReqDTOList) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(bucketService.createBucketList(tripId));
    }


}
