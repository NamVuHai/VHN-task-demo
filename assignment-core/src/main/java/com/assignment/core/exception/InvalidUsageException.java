package com.assignment.core.exception;

import com.assignment.core.constant.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvalidUsageException extends ApplicationException {

    public InvalidUsageException(String message) {
        super(ErrorCode.INVALID_EXECUTE, message);
    }


}
