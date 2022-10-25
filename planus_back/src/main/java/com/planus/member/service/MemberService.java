package com.planus.member.service;

import com.planus.member.dto.MemberResDTO;

import java.util.List;

public interface MemberService {
    List<MemberResDTO> findMemberList(long tripId);
}
