package com.planus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {
    // 클라이언트에서 WebSocket에 접속할 수 있는 경로
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        WebSocketMessageBrokerConfigurer.super.registerStompEndpoints(registry);
        // 핸드셰이크 생성할 경로?
        registry.addEndpoint("/ws").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        WebSocketMessageBrokerConfigurer.super.configureMessageBroker(registry);
        // /test로 시작하는 STOMP 메시지의 destination 헤더는 @Controller의 @MessageMapping 메서드로 라우팅
        // client에서 SEND 요청을 처리, client에게서 메시지를 받을 때
        registry.setApplicationDestinationPrefixes("/app"); // /pub
        // 이 주소를 구독하는 client에게 메시지를 전달할 때 (topic : 1:N)
        registry.enableSimpleBroker("/topic");  // /sub
    }


}
