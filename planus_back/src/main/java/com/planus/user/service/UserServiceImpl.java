package com.planus.user.service;

import com.planus.db.entity.Member;
import com.planus.db.entity.Trip;
import com.planus.db.entity.User;
import com.planus.db.repository.*;
import com.planus.mytrip.service.MyTripService;
import com.planus.plan.dto.PlanResDTO;
import com.planus.user.dto.UserInfoResDTO;
import com.planus.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    private final TripRepository tripRepository;

    private final MemberRepository memberRepository;

    @Override
    public User join(String nickname, String email, Long kakaoId, String imageUrl) {
        User user = userRepository.findOneByKakaoId(kakaoId);
        if(user==null){
            user = User.builder()
                    .name(nickname)
                    .email(email)
                    .kakaoId(kakaoId)
                    .refreshToken(tokenProvider.createRefreshToken())
                    .imageUrl(imageUrl)
                    .build();
            userRepository.save(user);
        }else{
            user.setRefreshToken(tokenProvider.createRefreshToken());
            user.setImageUrl(imageUrl);
            userRepository.save(user);
        }
        return user;
    }

    @Override
    public UserInfoResDTO findUserInfo(long userId) {
        User user = userRepository.findByUserId(userId);
        return new UserInfoResDTO(user.getName(), user.getEmail());
    }

    @Override
    @Transactional
    public String updateUser(String token, String nickname) {
        long userId = tokenProvider.getUserId(token);
        User user = userRepository.findByUserId(userId);
        user.setName(nickname);
        String newToken = tokenProvider.updateTokenNickname(token, nickname);

        return newToken;
    }

    @Override
    @Transactional
    public long deleteUser(long userId) {
        userRepository.deleteById(userId);
        return userId;
    }

    @Override
    public User findUserByKakaoId(Long kakaoId) {
        return userRepository.findOneByKakaoId(kakaoId);
    }

    @Override
    public long findKakaoIdByUserId(long userId) {
        User user = userRepository.findByUserId(userId);
        return user.getKakaoId();
    }

    @Override
    public String makeNewAccessToken(String refreshToken) {
        User user = userRepository.findByRefreshTokenContains(refreshToken);
        if(user!=null) {
            String newAccessToken = tokenProvider.createNewAccessToken(user);
            return newAccessToken;
        }else{
            return null;
        }
    }

    @Override
    @Transactional
    public void logout(String token) {
        long userId = tokenProvider.getUserId(token);
        User user = userRepository.findByUserId(userId);
        user.setRefreshToken(null);
    }

    @Override
    @Transactional
    public void changeAdminForSignOut(long userId) {
        User user = userRepository.findByUserId(userId);
        List<Member> memberList = user.getMemberList();
        for (Member member : memberList) {
            Trip trip = member.getTrip();
            long tripId = trip.getTripId();
            if(trip.isComplete()) {
                memberRepository.deleteByTripTripIdAndUserUserId(tripId, userId);
                if(trip.getAdmin() == userId)
                    trip.changeAdmin(0);
                continue;
            }
            if(trip.getAdmin() != userId) {
                memberRepository.deleteByTripTripIdAndUserUserId(tripId, userId);
                continue;
            }
            if(memberRepository.countByTripTripId(tripId) > 1) {
                memberRepository.deleteByTripTripIdAndUserUserId(tripId, userId);
                trip.changeAdmin(memberRepository.findTop1ByTripTripId(tripId).getUser().getUserId());
                continue;
            }
            // 진행중 일정 - 방장 - 잔여 참가자 X (참가자에서 삭제, 전체 일정 삭제)
            tripRepository.deleteById(tripId);
        }

    }
}
