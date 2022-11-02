package com.planus.trip.service;

import com.planus.trip.dto.MemberResDTO;
import com.planus.websocket.model.WebSocketMember;

import java.util.List;

public interface MemberService {
    long addMember(String token, long tripId);
    List<MemberResDTO> findMemberList(long tripId);
    void addConnector(WebSocketMember member);
    void delConnector(WebSocketMember member);
    int[] getConnector(WebSocketMember member);
}
