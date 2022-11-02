package com.planus.trip.service;

import com.planus.db.entity.Member;
import com.planus.db.repository.MemberRepository;
import com.planus.db.repository.TripRepository;
import com.planus.db.repository.UserRepository;
import com.planus.trip.dto.MemberResDTO;
import com.planus.util.TokenProvider;
import com.planus.websocket.model.WebSocketMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class MemberServiceImpl implements MemberService{
    private TokenProvider tokenProvider;
    private MemberRepository memberRepository;
    private UserRepository userRepository;
    private TripRepository tripRepository;
    private RedisTemplate<String, Long> redisTemplate;

    @Autowired
    public MemberServiceImpl(TokenProvider tokenProvider, MemberRepository memberRepository, UserRepository userRepository, TripRepository tripRepository, RedisTemplate<String, Long> redisTemplate){
        this.tokenProvider = tokenProvider;
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
        this.tripRepository = tripRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public long addMember(String token, long tripId) {
        if(memberRepository.existsByTripTripIdAndUserUserId(tripId, tokenProvider.getUserId(token.split(" ")[1]))){
            return -2;
        }else if(memberRepository.countByTripTripId(tripId)>9){
            return -1;
        }else{
            Member member = Member.builder()
                    .user(userRepository.findByUserId(tokenProvider.getUserId(token.split(" ")[1])))
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

    @Override
    public void addConnector(WebSocketMember member) {
        String key = "connectorList::" + member.getTripId();

        SetOperations<String, Long> setOps = redisTemplate.opsForSet();
        setOps.add(key, tokenProvider.getUserId(member.getToken().split(" ")[1]));
    }

    @Override
    public void delConnector(WebSocketMember member) {
        String key = "connectorList::" + member.getTripId();

        SetOperations<String, Long> setOps = redisTemplate.opsForSet();
        setOps.remove(key,tokenProvider.getUserId(member.getToken().split(" ")[1]));
    }

    @Override
    public Integer[] getConnector(WebSocketMember member) {
        String key = "connectorList::" + member.getTripId();

        SetOperations<String, Long> setOps = redisTemplate.opsForSet();
        Set<Long> connectorSet= setOps.members(key);

        Integer[] connectorArray = connectorSet.toArray(new Integer[0]);

        return connectorArray;
    }
}
