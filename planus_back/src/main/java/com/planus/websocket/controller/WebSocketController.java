package com.planus.websocket.controller;

import com.planus.bucket.service.BucketService;
import com.planus.websocket.model.WebSocketBucket;
import com.planus.websocket.model.WebSocketMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ws")
@RequiredArgsConstructor
public class WebSocketController {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);
    private final SimpMessageSendingOperations sendingOperations;
    private static final String ROOT_URL = "/topic/planus/";

    private final BucketService bucketService;

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
//        TODO: redis에 버킷리스트 저장하기
        sendingOperations.convertAndSend(ROOT_URL+bucket.getTripId(),bucket);
    }
    @MessageMapping("/delBucket")
    public void delBucket(WebSocketBucket bucket){
        bucket.setAction(3);
//        TODO: redis에 해당 버킷리스트 삭제하기
        bucketService.deleteBucket(bucket.getTripId(), bucket);
        sendingOperations.convertAndSend(ROOT_URL+bucket.getTripId(),bucket);
    }
}
