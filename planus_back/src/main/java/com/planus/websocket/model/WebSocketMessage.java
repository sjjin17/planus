package com.planus.websocket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketMessage {
    private long tripId;
    private String userName;
    private String chatMsg;
    private int action; // 0:입장, 1:채팅 ...
}
