package com.planus.mytrip.controller;

import com.planus.mytrip.dto.MyTripListResDTO;
import com.planus.mytrip.service.MyTripService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/mytrip")
public class MyTripController {
    private final MyTripService mytripService;

    @GetMapping("/made")
    public ResponseEntity<MyTripListResDTO> getMadeTripList(@RequestHeader String Authorization, @PageableDefault(size = 6, sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok().body(mytripService.getMadeTripList(Authorization, pageable));
    }

    @GetMapping("/shared")
    public ResponseEntity<MyTripListResDTO> getSharedTripList(@RequestHeader String Authorization, @PageableDefault(size = 6, sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok().body(mytripService.getSharedTripList(Authorization, pageable));
    }
}
