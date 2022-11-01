package com.planus.websocket.controller;

import com.planus.bucket.service.BucketService;
import com.planus.trip.service.MemberService;
import com.planus.websocket.model.WebSocketBucket;
import com.planus.websocket.model.WebSocketMember;
import com.planus.websocket.model.WebSocketMessage;
import com.planus.websocket.model.WebSocketPlan;
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

import java.io.IOException;

@RestController
@RequestMapping("/ws")
@RequiredArgsConstructor
public class WebSocketController {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);
    private final SimpMessageSendingOperations sendingOperations;
    private static final String ROOT_URL = "/topic/planus/";

    private final BucketService bucketService;
    private final MemberService memberService;

    @MessageMapping("/enter")
    public void enter(WebSocketMember member, SimpMessageHeaderAccessor headerAccessor){
        try{
            //퇴장이벤트 처리를 위해 token, tripId 웹소켓세션에 데이터 저장
            long tripId = member.getTripId();
            String token = member.getToken();

            //redis 접속자 목록에 추가
            memberService.addConnector(member);

            headerAccessor.getSessionAttributes().put("token",token);
            headerAccessor.getSessionAttributes().put("tripId",tripId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @MessageMapping("/connector")
    public void getConnector(WebSocketMember member){
        memberService.getConnector(member);
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
    @MessageMapping("/addPlan")
    public void addPlan(WebSocketPlan plan){
        plan.setAction(4);
//        TODO: redis에 해당 timetable 추가하기
        sendingOperations.convertAndSend(ROOT_URL+plan.getTripId(),plan);
    }
    @MessageMapping("/delPlan")
    public void delPlan(WebSocketPlan plan){
        plan.setAction(5);
//        TODO: redis에 해당 timetable 삭제하기
        sendingOperations.convertAndSend(ROOT_URL+plan.getTripId(),plan);
    }

    @EventListener
    public void exit(SessionDisconnectEvent event) {
        //연결된 모든 클라이언트에게 사용자 퇴장 이벤트 처리
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        try {
            WebSocketMember member = new WebSocketMember();

            //웹소켓세션에서 token과 tripId 가져오기
            member.setTripId((int) headerAccessor.getSessionAttributes().get("tripId"));
            member.setToken(headerAccessor.getSessionAttributes().get("token").toString());

            //redis 접속자 목록에서 삭제
            memberService.delConnector(member);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //웹소켓세션 비우기
        headerAccessor.getSessionAttributes().clear();
    }
}
