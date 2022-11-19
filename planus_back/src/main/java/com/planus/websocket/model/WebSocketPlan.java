package com.planus.websocket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketPlan {
    private long tripId;
    private long planId;
    private LocalDate tripDate;
    private int startTime;

    // 0:입장, 1:채팅, 2:버킷리스트 추가, 3: 버킷리스트 삭제, 4: 일정(plan)변경, 5:일정(timetable)추가, 6:일정(timetable)삭제 ...
    private int action;






}
