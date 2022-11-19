package com.planus.user.dto;

import com.planus.db.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserResDTO {
    private long userId;
    private String name;
    private String email;

    @Builder
    public UserResDTO(long userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }


    public static UserResDTO toResDto(User user) {
        return UserResDTO.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
