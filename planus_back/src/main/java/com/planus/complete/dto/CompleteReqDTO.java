package com.planus.complete.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompleteReqDTO {
    private long tripId;
    private String startDate;
}
