package com.planus.websocket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketMessage {
    private double tripId;
    private String userName;
    private String chatMsg;
    private int action; // 0:입장, ...

}
