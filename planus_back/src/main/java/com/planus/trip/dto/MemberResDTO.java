package com.planus.trip.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResDTO {
    private long userId;
    private String name;
    private String email;

    public MemberResDTO(long userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }
}
