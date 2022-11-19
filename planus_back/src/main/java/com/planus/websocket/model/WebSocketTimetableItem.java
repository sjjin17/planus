package com.planus.websocket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketTimetableItem {
    private String place;
    private double lat;
    private double lng;
    private int orders;
    private int costTime;
    private int moveTime;
    private String transit;
    private String moveRoute;

    // 0:입장, 1:채팅, 2:버킷리스트 추가, 3: 버킷리스트 삭제, 4: 일정(plan)변경, 5:일정(timetable)추가, 6:일정(timetable)삭제 ...
    private int action;






}
