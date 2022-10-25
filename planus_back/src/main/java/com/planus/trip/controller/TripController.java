package com.planus.trip.controller;

import com.planus.trip.dto.TripReqDTO;
import com.planus.trip.service.AreaService;
import com.planus.trip.service.MemberService;
import com.planus.trip.service.TripService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/trip")
public class TripController {

    private static final Logger logger = LoggerFactory.getLogger(TripController.class);
    private final AreaService areaService;
    private final MemberService memberService;
    private final TripService tripService;

    @Autowired
    public TripController(AreaService areaService, MemberService memberService, TripService tripService) {
        this.areaService = areaService;
        this.memberService = memberService;
        this.tripService = tripService;
    }

    @GetMapping
    public ResponseEntity getTripInfo(@RequestParam long tripId){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap.put("result", tripService.findTripInfo(tripId));
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity createTrip(@RequestBody TripReqDTO tripReqDTO){
        long admin = tripReqDTO.getAdmin();
        LocalDate startDate = tripReqDTO.getStartDate();
        int period = tripReqDTO.getPeriod();
        int[] areaId = tripReqDTO.getAreaId();

        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap.put("result", tripService.createTrip(admin, startDate, period, areaId));
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/area")
    public ResponseEntity getAllArea(){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap.put("areaList", areaService.findAllArea());
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/member")
    public ResponseEntity getMemberList(@RequestParam long tripId){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap.put("memberList", memberService.findMemberList(tripId));
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
