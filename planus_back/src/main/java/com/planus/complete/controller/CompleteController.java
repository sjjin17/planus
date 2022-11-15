package com.planus.complete.controller;

import com.planus.complete.dto.CompleteResDTO;
import com.planus.complete.service.CompleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/complete")
@RequiredArgsConstructor
public class CompleteController {

    private final CompleteService completeService;

    @GetMapping("/{uuid}")
    public ResponseEntity getCompleteTrip(@PathVariable(name="uuid") String uuid) {
        Map<String, Object> resultMap = new HashMap();
        try {
            CompleteResDTO completeTrip = completeService.getCompleteTrip(uuid);

            resultMap.put("message", "success");
            resultMap.put("result", completeTrip);

            return new ResponseEntity(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            resultMap.put("message", "완료여행 받기 실패");

            return new ResponseEntity(resultMap, HttpStatus.BAD_REQUEST);
        }

    }

}
