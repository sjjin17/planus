package com.planus.mytrip.controller;

import com.planus.mytrip.dto.MyTripListResDTO;
import com.planus.mytrip.dto.MyTripResDTO;
import com.planus.mytrip.service.MyTripService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    // 내가 참가자 또는 방장인 모든 여행 정보(최근 작성일 순)
    @GetMapping("/all")
    public ResponseEntity<List<MyTripResDTO>> getMyTripList(@RequestHeader String Authorization) {
        return ResponseEntity.ok().body(mytripService.getMyTripList(Authorization));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteTrip(@RequestHeader String Authorization, Long tripId) {
        return ResponseEntity.ok().body(mytripService.deleteTrip(Authorization, tripId));
    }
}
