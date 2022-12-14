package com.planus.websocket.controller;

import com.planus.bucket.service.BucketService;
import com.planus.db.entity.Plan;
import com.planus.db.repository.PlanRepository;
import com.planus.db.entity.Trip;
import com.planus.db.repository.UserRepository;
import com.planus.plan.service.PlanService;
import com.planus.trip.service.MemberService;
import com.planus.trip.service.TripService;
import com.planus.util.TokenProvider;
import com.planus.websocket.model.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.ArrayList;
import java.util.Collections;
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
    private final MemberService memberService;

    private final TripService tripService;

    private final PlanRepository planRepository;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    private static List<String> sessionUsers = Collections.synchronizedList(new ArrayList<>());

    @MessageMapping("/enter")
    public void enter(WebSocketMember member, SimpMessageHeaderAccessor headerAccessor){
        try{
            //??????????????? ????????? ?????? token, tripId ?????????????????? ????????? ??????
            long tripId = member.getTripId();
            String token = member.getToken();

            //redis ????????? ????????? ??????
            memberService.addConnector(member);
            headerAccessor.getSessionAttributes().put("token",token);
            headerAccessor.getSessionAttributes().put("tripId",tripId);

            sessionUsers.add(userRepository.findByUserId(tokenProvider.getUserId(token.split(" ")[1])).getName());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @MessageMapping("/connector")
    public void getConnector(WebSocketMember member){
        member.setConnector(memberService.getConnector(member));
        member.setAction(0);
        try{
            sendingOperations.convertAndSend(ROOT_URL+member.getTripId(),member);
        }catch (Exception e){
            e.printStackTrace();
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
        bucketService.addBucket(bucket);
        sendingOperations.convertAndSend(ROOT_URL+bucket.getTripId(),bucket);
    }

    @MessageMapping("/delBucket")
    public void delBucket(WebSocketBucket bucket){
        bucket.setAction(3);
        bucketService.deleteBucket(bucket);
        sendingOperations.convertAndSend(ROOT_URL+bucket.getTripId(),bucket);
    }

    @MessageMapping("/setPlan")
    public void setPlan(WebSocketPlan plan){
        plan.setAction(4);
//        TODO: redis??? ?????? Plan ??????
        planService.setPlan(plan.getPlanId(), plan);
        sendingOperations.convertAndSend(ROOT_URL+plan.getTripId(),plan);
    }

    @MessageMapping("/addTimetable")
    public void addTimetable(WebSocketTimetable timetable){
        timetable.setAction(5);
        if(timetable.getFromBucket()){
            // TODO: bucket redis ??????
            bucketService.moveToPlan(timetable);

        }
//        TODO: redis??? ?????? timetable ????????????
        planService.addTimetable(timetable.getPlanId(), timetable);
        sendingOperations.convertAndSend(ROOT_URL+timetable.getTripId(),timetable);
    }

    @MessageMapping("/delTimetable")
    public void delTimetable(WebSocketTimetableList timetablelist){
        timetablelist.setAction(6);
//        TODO: redis??? ?????? timetable ????????????
        planService.delTimetable(timetablelist.getPlanId(), timetablelist);
        sendingOperations.convertAndSend(ROOT_URL+timetablelist.getTripId(),timetablelist);
    }

    @MessageMapping("/setTimetableOrders")
    public void setTimetableOrders(WebSocketTimetableList timetableList) {
        timetableList.setAction(7);
        planService.setTimetableOrders(timetableList.getPlanId(), timetableList);
        sendingOperations.convertAndSend(ROOT_URL+timetableList.getTripId(),timetableList);
    }

    @MessageMapping("/setTimetable")
    public void setTimetable(WebSocketTimetable Timetable) {
        Timetable.setAction(8);
        planService.setTimetable(Timetable.getPlanId(), Timetable);
        sendingOperations.convertAndSend(ROOT_URL+Timetable.getTripId(),Timetable);
    }


    @MessageMapping("/changeAdmin")
    public void changeAdmin(WebSocketAdmin admin){
        admin.setAction(9);
        long originAdminId = admin.getOriginAdminId();
        Trip trip = tripService.findByTripId(admin.getTripId());
        if(trip.getAdmin()!=originAdminId){
            return;
        }else if (!memberService.isMemberOfTrip(admin.getTripId(), admin.getNewAdminId())){
            return;
        }else{
            tripService.changeAdminForWebSocket(trip,admin.getNewAdminId());
            sendingOperations.convertAndSend(ROOT_URL+admin.getTripId(), admin);
        }
    }

    @MessageMapping("/completeTrip")
    public void completeTrip(WebSocketComplete complete) {
        complete.setAction(10);
        sendingOperations.convertAndSend(ROOT_URL+complete.getTripId(),complete);
    }

    @EventListener
    public void exit(SessionDisconnectEvent event) {
        //????????? ?????? ????????????????????? ????????? ?????? ????????? ??????
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        try {
            WebSocketMember member = new WebSocketMember();

            //????????????????????? token??? tripId ????????????
            member.setTripId((long)headerAccessor.getSessionAttributes().get("tripId"));
            member.setToken(headerAccessor.getSessionAttributes().get("token").toString());

            //redis ????????? ???????????? ??????
            memberService.delConnector(member);

            String nickname = userRepository.findByUserId(tokenProvider.getUserId(member.getToken().split(" ")[1])).getName();
            sessionUsers.remove(nickname);


        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sessionUsers.size() == 0) {
            // ??????????????? ??? ?????? ????????? ????????? redis??? ?????? ?????? mysql??? ??????
            List<Plan> planIdList = planRepository.findByTripTripId((long)headerAccessor.getSessionAttributes().get("tripId")).orElseThrow();
            for (Plan p:planIdList) {
                planService.savePlan(p.getPlanId(), false);
                planService.saveTimetable(p.getPlanId(), false);
            }
            bucketService.createBucketList((long)headerAccessor.getSessionAttributes().get("tripId"));
        }




        //??????????????? ?????????
        headerAccessor.getSessionAttributes().clear();
    }
}
