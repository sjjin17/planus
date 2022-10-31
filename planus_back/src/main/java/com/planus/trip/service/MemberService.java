package com.planus.trip.service;

import com.planus.trip.dto.MemberResDTO;

import java.util.List;

public interface MemberService {

    long addMember(String token, long tripId);
    List<MemberResDTO> findMemberList(long tripId);
}
