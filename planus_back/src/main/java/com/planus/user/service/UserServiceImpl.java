package com.planus.user.service;

import com.planus.db.entity.User;
import com.planus.user.dto.UserInfoResDTO;
import com.planus.db.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User join(String nickname, String email, Long kakaoId) {
        User user = userRepository.findOneByKakaoId(kakaoId);
        if(user==null){
            user = User.builder()
                    .name(nickname)
                    .email(email)
                    .kakaoId(kakaoId)
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
    public long updateUser(long userId, String nickname) {
        User user = userRepository.findByUserId(userId);
        user.setName(nickname);
        return user.getUserId();
    }

    @Override
    @Transactional
    public long deleteUser(long userId) {
        userRepository.deleteById(userId);
        return userId;
    }
}
