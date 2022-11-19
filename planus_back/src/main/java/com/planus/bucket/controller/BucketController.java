package com.planus.bucket.controller;

import com.planus.article.controller.ArticleController;
import com.planus.bucket.service.BucketService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/buckets")
public class BucketController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private final BucketService bucketService;

    @GetMapping("/{tripId}")
    public ResponseEntity getAllBuckets(@PathVariable Long tripId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(bucketService.findAllBuckets(tripId));
    }

    @PostMapping("/{tripId}")
    public ResponseEntity createBucketList(@PathVariable Long tripId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(bucketService.createBucketList(tripId));
    }


}
