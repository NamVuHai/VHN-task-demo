package com.assignment.core.exception;

import lombok.Data;

@Data
public class ErrorVo {
    private String code;
    private String message;

    public ErrorVo(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
