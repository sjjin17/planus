package com.planus.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse {
    private boolean success;
    private String message;

    public ErrorResponse(String message) {
        success = false;
        this.message = message;
    }
}
