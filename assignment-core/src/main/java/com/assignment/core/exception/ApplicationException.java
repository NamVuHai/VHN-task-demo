package com.assignment.core.exception;

import com.assignment.core.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public abstract class ApplicationException extends RuntimeException {
    private ErrorCode errorCode;
    private String message;
}
