package com.planus.user.service;

import com.planus.user.dto.UserInfoResDTO;

public interface UserService {


    UserInfoResDTO findUserInfo(long userId);
    long updateUser(long userId, String nickname);
    long deleteUser(long userId);

    long join();
}
