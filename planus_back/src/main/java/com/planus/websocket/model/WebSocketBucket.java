package com.planus.websocket.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketBucket {
    private long tripId;
    private String place;
    private String address;
    private double lat;
    private double lng;
    private int action; // 0:입장, 1:채팅 2:버킷리스트 추가 3: 버킷리스트 삭제 ...






}
