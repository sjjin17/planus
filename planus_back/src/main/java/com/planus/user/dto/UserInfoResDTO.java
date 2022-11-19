package com.planus.user.dto;

import lombok.Getter;

@Getter
public class UserInfoResDTO {

    private String nickname;

    private String email;

    public UserInfoResDTO(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }
}
