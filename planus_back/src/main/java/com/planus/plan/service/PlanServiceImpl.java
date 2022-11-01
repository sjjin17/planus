package com.planus.plan.service;

import com.planus.db.entity.Plan;
import com.planus.db.entity.Timetable;
import com.planus.db.entity.Transit;
import com.planus.db.repository.PlanRepository;
import com.planus.db.repository.TimetableRepository;
import com.planus.plan.dto.*;
import com.planus.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {
    private final PlanRepository planRepository;
    private final TimetableRepository timetableRepository;
//    private final RedisTemplate<String, PlanResDTO> redisTemplate;
    private final RedisUtil redisUtil;

//    public PlanServiceImpl(PlanRepository planRepository, TimetableRepository timetableRepository) {
//        this.planRepository = planRepository;
//        this.timetableRepository = timetableRepository;
//    }

    // redis에 plan 정보가 있으면 redis, 없으면 mysql에서 plan 읽어오기

    @Override
    public List<PlanIdResDTO> readPlanId(long tripId) {
        List<PlanIdResDTO> planIdResDTOList = new ArrayList<>();

        List<Plan> planList = planRepository.findByTripTripId(tripId).orElseThrow();
        for (Plan p : planList) {
            PlanIdResDTO planIdResDTO = PlanIdResDTO.builder().planId(p.getPlanId()).build();
            planIdResDTOList.add(planIdResDTO);
        }

        return planIdResDTOList;
    }

    @Override
    public List<PlanResDTO> readPlanList(List<Long> planIdList) {
        List<PlanResDTO> planResDTOList = new ArrayList<>();

        // 각 plan별로 for문을 돌면서 redis에 있는지 없는지를 검사함
        for (long planId : planIdList) {
//            HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
            String planKey = "planList::" + planId;
            String timetableKey = "timetableList::" + planId;

            // redis에 값이 있으면 redis에서 가져오고,
            if (redisUtil.isExists(planKey)) {
                //planList::planId 를 key로 가진 hash에서 startTime, tripDate를 가져올 수 있음
                Map<String, Object> map = redisUtil.getHashData(planKey);
                List list = redisUtil.getListData(timetableKey);

                // "timetableList::(planId)" : "{"orders":"1","place":"서울","transit":"CAR"}", "{"orders":"2","place":"부산","transit":"CAR"}"
                try {
                    List<TimetableListResDTO> timetableListResDTOList = new ArrayList<>();
                    JSONParser parser = new JSONParser();
                    for (int i = 0; i < list.size(); i++) {
                        JSONObject jsonObject = (JSONObject) parser.parse((String) list.get(i));

                        TimetableListResDTO timetableListResDTO = TimetableListResDTO.builder()
//                                .timetableId()
                                .orders((int) jsonObject.get("orders"))
                                .place((String) jsonObject.get("place"))
                                .lat((double) jsonObject.get("lat"))
                                .lng((double) jsonObject.get("lng"))
                                .transit((Transit) jsonObject.get("transit"))
                                .costTime((int) jsonObject.get("cost_time"))
                                .moveTime((int) jsonObject.get("move_time"))
                                .build();

                        timetableListResDTOList.add(timetableListResDTO);
                    }

                    PlanResDTO planResDTO = PlanResDTO.builder()
                            .planId(planId)
                            .tripDate((LocalDate) map.get("trip_date"))
                            .startTime((int) map.get("start_time"))
                            .timetableList(timetableListResDTOList)
                            .build();

                    planResDTOList.add(planResDTO);

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            }

            // 없으면 mysql에서 가져오기
            else {
                Plan plan = planRepository.findByPlanId(planId).orElseThrow();
                List<Timetable> timetableList = timetableRepository.findByPlanPlanId(planId).orElseThrow();
                List<TimetableListResDTO> timetableListResDTOList = new ArrayList<>();

                for (Timetable t : timetableList) {
                    //timetable Builder
                    TimetableListResDTO timetableListResDTO = TimetableListResDTO.builder()
                            .timetableId(t.getTimetableId())
                            .orders(t.getOrders())
                            .place(t.getPlace())
                            .lat(t.getLat())
                            .lng(t.getLng())
                            .costTime(t.getCostTime())
                            .transit(t.getTransit())
                            .moveTime(t.getMoveTime())
                            .build();

                    timetableListResDTOList.add(timetableListResDTO);
                }

                //plan Builder
                PlanResDTO planResDTO = PlanResDTO.builder()
                        .planId(plan.getPlanId())
                        .tripDate(plan.getTripDate())
                        .startTime(plan.getStartTime())
                        .timetableList(timetableListResDTOList)
                        .build();

                planResDTOList.add(planResDTO);
            }

        }

        return planResDTOList;
    }

    @Override
    @CacheEvict(cacheNames = "planList", key = "#planId")
    public void savePlan(long planId) {
//        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        String key = "planList::" + planId;

        Map<String, Object> map = redisUtil.getHashData(key);

        Plan plan = Plan.builder()
                .planId(planId)
                .tripDate((LocalDate) map.get("trip_date"))
                .startTime((int) map.get("start_time"))
                .build();

        planRepository.save(plan);
    }

    //redis에서 여행정보를 가져와서 mysql에 저장
    @Override
    @CacheEvict(cacheNames = "timetableList", key = "#planId")
    public void saveTimetable(long planId) {
        //일별일정 수정(저장)
        //기존의 timetable 삭제
        //새로운 timetable로 저장
        //새로운 timetable과 redis에서 가져온 map 정보들로 plan 객체 만들어서 저장

//            HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        String key = "timetableList::" + planId;

        List list = redisUtil.getListData(key);

        //mysql에 저장되어 있는 timetable을 전부 삭제
        timetableRepository.deleteByPlanPlanId(planId);

        try {
            JSONParser parser = new JSONParser();
//            String jsonString = (String) map.get("timetables");
//            JSONObject jsonObj = (JSONObject) parser.parse(jsonString);
//            JSONArray timetables = (JSONArray) jsonObj.get("timetables");

            for (int i = 0; i < list.size(); i++) {
                JSONObject jsonObject = (JSONObject) parser.parse((String) list.get(i));
                Timetable timetable = Timetable.builder()
                        .orders((int) jsonObject.get("orders"))
                        .place((String) jsonObject.get("place"))
                        .lat((double) jsonObject.get("lat"))
                        .lng((double) jsonObject.get("lng"))
                        .transit((Transit) jsonObject.get("transit"))
                        .costTime((int) jsonObject.get("cost_time"))
                        .moveTime((int) jsonObject.get("move_time"))
                        .build();

                timetableRepository.save(timetable);
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}
