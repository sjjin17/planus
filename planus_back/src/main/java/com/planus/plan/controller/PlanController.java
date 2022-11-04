package com.planus.plan.controller;

import com.planus.plan.dto.PlanIdResDTO;
import com.planus.plan.dto.PlanResDTO;
import com.planus.plan.service.PlanService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/plans")
public class PlanController {
    @Autowired
    PlanService planService;

    @GetMapping("/{tripId}")
    @ApiOperation(value = "planId 목록 조회", notes = "tripId를 이용해 일정 정보를 불러오는 데 필요한 planId의 목록들을 받아옵니다.")
    public ResponseEntity getPlanId(@PathVariable long tripId) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            List<PlanIdResDTO> planIdResDTOList = planService.readPlanId(tripId);
            resultMap.put("planIdList", planIdResDTOList);
            resultMap.put("message", "success");
            return new ResponseEntity(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("message", "planId 조회 오류");
            return new ResponseEntity(resultMap, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/planlist")
    @ApiOperation(value = "planList 정보 조회", notes = "planId 목록을 이용해 일정 정보를 불러옵니다.")
    public ResponseEntity getPlanList(@RequestBody List<Long> planIdList) {
        System.out.println(planIdList.toArray().toString());

        Map<String, Object> resultMap = new HashMap<>();

        try {
            List<PlanResDTO> planResDTOList = planService.readPlanList(planIdList);
            resultMap.put("planList", planResDTOList);
            resultMap.put("message", "success");

            System.out.println("getPlanList출력문");
            
            return new ResponseEntity(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("message", "planList 조회 오류");
            return new ResponseEntity(resultMap, HttpStatus.BAD_REQUEST);
        }
    }

    //redis에 먼저 출발시간 변경사항을 저장한 후, 이 api를 호출해야 함
    @GetMapping("/start/{planId}")
    @ApiOperation(value = "Plan 출발시간만 저장")
    public ResponseEntity savePlanStart(@PathVariable long planId) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            planService.savePlan(planId);
            resultMap.put("message", "success");
            return new ResponseEntity(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("message", "일정 출발 시간 저장 오류");
            return new ResponseEntity(resultMap, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping
    @ApiOperation(value = "Plan 및 Timetable 저장")
    public ResponseEntity savePlan(@RequestBody List<Long> planIdList) {
        // 더미데이터 만드는 구간
        for (long planId:planIdList) {
            Map<String, Object> map = new HashMap<>();
            map.put("trip_date", "");
            map.put("start_time", "480");
            //JSON으로 String화한 문자열을 저장해야 함
            map.put("timetables", "");
        }

        // 더미데이터 만드는 구간 끝


        Map<String, Object> resultMap = new HashMap<>();

        try {
            for (long planId:planIdList) {
                planService.savePlan(planId);
                planService.saveTimetable(planId);
            }
            resultMap.put("message", "success");
            return new ResponseEntity(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("message", "일정 저장 오류");
            return new ResponseEntity(resultMap, HttpStatus.BAD_REQUEST);
        }
    }

}
