package com.planus.mytrip.controller;

import com.planus.mytrip.service.MyTripService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/mytrip")
public class MyTripController {
    private final MyTripService mytripService;

//    @GetMapping
//    public ResponseEntity<MytripListResDTO> getBookSearchResult(@PageableDefault(sort = "isbn", direction = Sort.Direction.DESC) Pageable pageable) {
//        return ResponseEntity.ok().body(mytripService.getSearchResult(pageable));
//    }
}
