package com.planus.user.service;

import com.planus.db.entity.User;
import com.planus.user.dto.UserInfoResDTO;
import com.planus.db.repository.UserRepository;
import com.planus.util.TokenProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    public UserServiceImpl(TokenProvider tokenProvider, UserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
    }

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

}
