package com.planus.trip.service;

import com.planus.db.entity.Member;
import com.planus.db.repository.MemberRepository;
import com.planus.db.repository.TripRepository;
import com.planus.db.repository.UserRepository;
import com.planus.trip.dto.MemberResDTO;
import com.planus.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService{
    private JwtUtil jwtUtil;
    private MemberRepository memberRepository;
    private UserRepository userRepository;
    private TripRepository tripRepository;

    @Autowired
    public MemberServiceImpl(JwtUtil jwtUtil, MemberRepository memberRepository, UserRepository userRepository, TripRepository tripRepository){
        this.jwtUtil = jwtUtil;
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
        this.tripRepository = tripRepository;
    }

    @Override
    public long addMember(String token, long tripId) {
        if(memberRepository.existsByTripTripIdAndUserUserId(tripId, jwtUtil.getUserIdFromToken(token.split(" ")[1]))){
            return -2;
        }else if(memberRepository.countByTripTripId(tripId)>9){
            return -1;
        }else{
            Member member = Member.builder()
                    .user(userRepository.findByUserId(jwtUtil.getUserIdFromToken(token.split(" ")[1])))
                    .trip(tripRepository.findByTripId(tripId))
                    .build();

            memberRepository.save(member);

            return member.getMemberId();
        }
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
