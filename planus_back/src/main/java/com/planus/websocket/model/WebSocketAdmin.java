package com.planus.websocket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketAdmin {
    long tripId;
    long newAdminId;
    long originAdminId;
    int action;
}
