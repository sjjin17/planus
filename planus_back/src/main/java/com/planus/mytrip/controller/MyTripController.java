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
    public ResponseEntity<MyTripListResDTO> getBookSearchResult(@RequestHeader(name="token") String token, @PageableDefault(sort = "isbn", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok().body(mytripService.getMadeTripList(token, pageable)); // 토큰 구현되면 바꾸기
    }
}
