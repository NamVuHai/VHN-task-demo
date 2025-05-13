package com.assignment.core.exception;

import com.assignment.core.constant.ErrorCode;


public class BusinessException extends ApplicationException {

    public BusinessException(ErrorCode code, String message) {
        super(code, message);
    }

    public BusinessException(ErrorCode code) {
        super(code, code.getDescription());
    }

}
