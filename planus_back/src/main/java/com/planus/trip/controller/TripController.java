package com.planus.trip.controller;

import com.planus.trip.dto.ChangeAdminReqDTO;
import com.planus.trip.dto.TripReqDTO;
import com.planus.trip.service.AreaService;
import com.planus.trip.service.MemberService;
import com.planus.trip.service.TripService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity getTripInfo(@RequestHeader(name="Authorization", required = false) String token, @RequestParam String tripUrl){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap.put("result", tripService.findTripInfo(token, tripUrl));
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity createTrip(@RequestHeader(name="Authorization") String token, @RequestBody TripReqDTO tripReqDTO){
        String startDate = tripReqDTO.getStartDate();
        long period = tripReqDTO.getPeriod();
        int[] areaId = tripReqDTO.getAreaId();

        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap.put("result", tripService.createTrip(token, startDate, period, areaId));
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

    @GetMapping("/bestarea")
    public ResponseEntity getBestArea(){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap.put("areaList", areaService.findBestArea());
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/festival")
    public ResponseEntity getFestival(@PageableDefault(size = 4)@SortDefault.SortDefaults({@SortDefault(sort = "endDate", direction = Sort.Direction.ASC),@SortDefault(sort = "title",direction = Sort.Direction.ASC)}) Pageable pageable){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap.put("festivalPage", areaService.findFestival(pageable));
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/member")
    public ResponseEntity addMember(@RequestHeader(name="Authorization") String token, @RequestParam long tripId){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap.put("memberId", memberService.addMember(token, tripId));
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

    @PutMapping("/changeadmin")
    public ResponseEntity changeAdmin(@RequestHeader String Authorization, @RequestBody ChangeAdminReqDTO dto){
        Map<String, Object> resultMap = new HashMap<>();

        String token = Authorization.substring(7);
        long tripId = dto.getTripId();
        long userId = dto.getUserId();
        //TODO: 차후에 exception으로 수정
        try{
            long newAdminId = tripService.changeAdmin(token, tripId, userId);
            if(newAdminId==-1){
                resultMap.put("message", "방장 바꾸기에서 문제 발생");
                return new ResponseEntity(resultMap, HttpStatus.BAD_REQUEST);
            }else{
                resultMap.put("message", "success");
                resultMap.put("newAdminId", newAdminId);
                return new ResponseEntity(resultMap, HttpStatus.OK);
            }
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
    }
}
