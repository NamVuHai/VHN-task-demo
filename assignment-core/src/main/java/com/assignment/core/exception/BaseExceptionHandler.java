package com.assignment.core.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseExceptionHandler {
    private static final ExceptionMapping DEFAULT_ERROR = new ExceptionMapping("SERVER_ERROR", "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final Logger logger;
    private final Map<Class, ExceptionMapping> exceptionMappingMap = new HashMap<>();

    public BaseExceptionHandler(final Logger logger) {
        this.logger = logger;
        registerMapping(MissingServletRequestParameterException.class, "MISSING_PARAMETER", "Missing request parameter", HttpStatus.BAD_REQUEST);
        registerMapping(MethodArgumentTypeMismatchException.class, "ARGUMENT_TYPE_MISMATCH", "Argument type mismatch", HttpStatus.BAD_REQUEST);
        registerMapping(HttpRequestMethodNotSupportedException.class, "METHOD_NOT_SUPPORTED", "HTTP method not supported", HttpStatus.METHOD_NOT_ALLOWED);
        registerMapping(ServletRequestBindingException.class, "MISSING_HEADER", "Missing header in request", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ErrorVo handleException(final Throwable throwable, final HttpServletResponse response) {
        ExceptionMapping exceptionMapping = exceptionMappingMap.getOrDefault(throwable.getClass(), DEFAULT_ERROR);
        response.setStatus(exceptionMapping.status.value());
        return logAndConstruct(exceptionMapping, throwable);
    }

    protected void registerMapping(final Class<?> clazz, final String code, final String message, final HttpStatus httpStatus) {
        exceptionMappingMap.put(clazz, new ExceptionMapping(code, message, httpStatus));
    }

    protected ErrorVo construct(ExceptionMapping exceptionMapping) {
        return new ErrorVo(exceptionMapping.code, exceptionMapping.message);
    }

    protected ErrorVo logAndConstruct(ExceptionMapping exceptionMapping, Throwable t) {
        logger.error(exceptionMapping.code, exceptionMapping.message, t);
        return construct(exceptionMapping);
    }


    private record ExceptionMapping(String code, String message, HttpStatus status) {
    }
}
