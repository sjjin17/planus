package com.planus.trip.service;

import com.planus.db.entity.Member;
import com.planus.db.repository.MemberRepository;
import com.planus.trip.dto.MemberResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService{
    private MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    @Override
    public List<MemberResDTO> findMemberList(long tripId) {
        List<MemberResDTO> memberResDTOList = new ArrayList<>();
        List<Member> memberList = memberRepository.findByTripTripId(tripId);
        for(Member member : memberList){
            MemberResDTO memberResDTO = MemberResDTO.builder()
                    .userId(member.getUser().getUserId())
                    .name(member.getUser().getName())
                    .email(member.getUser().getEmail())
                    .build();

            memberResDTOList.add(memberResDTO);
        }

        return memberResDTOList;
    }
}
