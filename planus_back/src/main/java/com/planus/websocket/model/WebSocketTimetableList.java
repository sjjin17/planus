package com.planus.websocket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketTimetableList {
    private long tripId;
    List<WebSocketTimetable> timetableList;
    // 0:입장, 1:채팅, 2:버킷리스트 추가, 3: 버킷리스트 삭제, 4: 일정(plan)변경, 5:일정(timetable)추가, 6:일정(timetable)삭제 ...
    private int action;






}
