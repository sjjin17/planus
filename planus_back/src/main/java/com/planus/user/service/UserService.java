package com.planus.user.service;

import com.planus.db.entity.User;
import com.planus.user.dto.UserInfoResDTO;

public interface UserService {


    UserInfoResDTO findUserInfo(long userId);
    String updateUser(String token, String nickname);
    long deleteUser(long userId);

    User join(String nickname, String email, Long kakaoId);

    long findKakaoIdByUserId(long userId);

    User findUserByKakaoId(Long kakaoId);
}
