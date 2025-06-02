package com.assignment.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Slf4j
public class ErrorHandler extends BaseExceptionHandler {


    public ErrorHandler() {
        super(log);
        registerMapping(NotFoundException.class, "USER_NOT_FOUND", "User not found", HttpStatus.NOT_FOUND);
    }
}
