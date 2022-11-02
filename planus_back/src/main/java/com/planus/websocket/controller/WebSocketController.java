package com.planus.websocket.controller;

import com.planus.bucket.service.BucketService;
import com.planus.plan.service.PlanService;
import com.planus.websocket.model.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ws")
@RequiredArgsConstructor
public class WebSocketController {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);
    private final SimpMessageSendingOperations sendingOperations;
    private static final String ROOT_URL = "/topic/planus/";

    private final BucketService bucketService;
    private final PlanService planService;

    @MessageMapping("/enter")
    public void enter(WebSocketMessage message, SimpMessageHeaderAccessor headerAccessor){
        message.setAction(0);
        long tripId = message.getTripId();
        String userName = message.getUserName();
        if(userName != null && tripId != 0){
            try{
                if(headerAccessor.getSessionAttributes() != null){
                    headerAccessor.getSessionAttributes().put("userName",userName);
                    headerAccessor.getSessionAttributes().put("tripId",tripId);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            logger.info("User["+userName+"] conneted");
        }
    }

    @MessageMapping("/chat")
    public void chat(WebSocketMessage message){
        message.setAction(1);
        sendingOperations.convertAndSend(ROOT_URL+message.getTripId(),message);
    }

    @MessageMapping("/addBucket")
    public void addBucket(WebSocketBucket bucket){
        bucket.setAction(2);
        bucketService.addBucket(bucket.getTripId(), bucket);
        sendingOperations.convertAndSend(ROOT_URL+bucket.getTripId(),bucket);
    }
    @MessageMapping("/delBucket")
    public void delBucket(WebSocketBucket bucket){
        bucket.setAction(3);
        bucketService.deleteBucket(bucket.getTripId(), bucket);
        sendingOperations.convertAndSend(ROOT_URL+bucket.getTripId(),bucket);
    }
    @MessageMapping("/setPlan")
    public void setPlan(WebSocketPlan plan){
        plan.setAction(4);
//        TODO: redis에 해당 Plan 수정
        planService.setPlan(plan.getPlanId(), plan);
        sendingOperations.convertAndSend(ROOT_URL+plan.getTripId(),plan);
    }


    @MessageMapping("/addTimetable")
    public void addTimetable(WebSocketTimetable Timetable){
        Timetable.setAction(5);
//        TODO: redis에 해당 timetable 추가하기
        planService.addTimetable(Timetable.getPlanId(), Timetable);
        sendingOperations.convertAndSend(ROOT_URL+Timetable.getTripId(),Timetable);
    }
    @MessageMapping("/delTimetable")
    public void delTimetable(WebSocketTimetableList TimetableList){
        TimetableList.setAction(6);
//        TODO: redis에 해당 timetable 삭제하기
        planService.delTimetable(TimetableList.getPlanId(), TimetableList);
        sendingOperations.convertAndSend(ROOT_URL+TimetableList.getTripId(),TimetableList);
    }

    @MessageMapping("/setTimetableOrders")
    public void setTimetableOrders(WebSocketTimetableList TimetableList) {
        TimetableList.setAction(7);
        planService.setTimetableOrders(TimetableList.getPlanId(), TimetableList);
        sendingOperations.convertAndSend(ROOT_URL+TimetableList.getTripId(),TimetableList);
    }

    @MessageMapping("/setTimetable")
    public void setTimetable(WebSocketTimetable Timetable) {
        Timetable.setAction(8);
        planService.setTimetable(Timetable.getPlanId(), Timetable);
        sendingOperations.convertAndSend(ROOT_URL+Timetable.getTripId(),Timetable);
    }
}
