package com.planus.user.service;

import com.planus.db.entity.User;
import com.planus.user.dto.UserInfoResDTO;

public interface UserService {


    UserInfoResDTO findUserInfo(String token);
    String updateUser(String token, String nickname);
    long deleteUser(long userId);

    User join(String nickname, String email, Long kakaoId, String imageUrl);

    long findKakaoIdByUserId(long userId);

    User findUserByKakaoId(Long kakaoId);

    String makeNewAccessToken(String refreshToken);

    void logout(String token);

    void changeAdminForSignOut(long userId);
}
