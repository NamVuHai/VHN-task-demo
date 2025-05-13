package com.assignment.core.exception;

import com.assignment.core.model.ResponseModel;
import com.ctc.wstx.util.StringUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.MethodNotAllowedException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ResponseExceptionHandler {
    @ExceptionHandler(value = {MethodNotAllowedException.class,
            HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ResponseModel<Object>> methodNotAllowed(Exception e) {
        ResponseModel<Object> responseModel = ResponseModel.error(e.getMessage());
        return ResponseEntity.status(METHOD_NOT_ALLOWED).body(responseModel);
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<ResponseModel<Object>> noResultException(NoResultException exception) {
        ResponseModel<Object> responseModel = ResponseModel.error(exception.getMessage());
        return ResponseEntity.status(NOT_FOUND).body(responseModel);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseModel<Object>> illegalArgumentException(IllegalArgumentException e) {
        ResponseModel<Object> responseModel = ResponseModel.error(e.getMessage());
        return ResponseEntity.status(BAD_REQUEST).body(responseModel);
    }

    @ExceptionHandler(BusinessException.class)
    public @ResponseBody ResponseEntity<ResponseModel<Object>> businessException(BusinessException e) {
        return new ResponseEntity<>(ResponseModel.error(e.getMessage()), BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public @ResponseBody ResponseEntity<ResponseModel<Object>> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException exception) {
        ResponseModel<Object> responseModel = ResponseModel.error(exception.getMessage());
        return ResponseEntity.status(BAD_REQUEST).body(responseModel);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public @ResponseBody ResponseEntity<ResponseModel<Object>> handleInvalidFormatException(
            InvalidFormatException exception) {
        ResponseModel<Object> responseModel = ResponseModel.error(exception.getMessage());
        return ResponseEntity.status(BAD_REQUEST).body(responseModel);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public @ResponseBody ResponseEntity<ResponseModel<Object>> methodArgumentNotValid(
            MethodArgumentNotValidException e) {
        String message = null;
        if (e.getBindingResult().getFieldError() != null) {
            FieldError fieldError = e.getBindingResult().getFieldError();
            if (fieldError != null) {
                message = fieldError.getDefaultMessage();
            }
        }
        return ResponseEntity.status(BAD_REQUEST).body(ResponseModel.error(message));
    }

    @ExceptionHandler({BindException.class})
    @ResponseStatus(BAD_REQUEST)
    public @ResponseBody ResponseEntity<ResponseModel<Object>> bindException(BindException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        String message = null;
        String defaultMessage = null;
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            List<String> allErrors = new ArrayList<>(errors.size());
            for (ObjectError error : errors) {
                if (StringUtils.isNotEmpty(defaultMessage = error.getDefaultMessage())) {
                    allErrors.add(defaultMessage.replace("\"", "'"));
                }
            }
            if (!allErrors.isEmpty()) {
                message = String.join(",", allErrors);
            }
        }
        return ResponseEntity.status(BAD_REQUEST).body(ResponseModel.error(message));
    }

    @ExceptionHandler(RuntimeException.class)
    public @ResponseBody ResponseEntity<ResponseModel<Object>> internalUnchecked(RuntimeException e) {
        log.error(e.getMessage(), e);
        Exception cause = (Exception) e.getCause();
        if (cause instanceof JsonParseException) {
            return ResponseEntity.status(BAD_REQUEST).body(ResponseModel.error("Parse Json Error"));
        }
        String error = cause != null ? cause.getMessage() : INTERNAL_SERVER_ERROR.name();
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ResponseModel.error(error));
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public @ResponseBody ResponseEntity<ResponseModel<Object>> internalUnchecked(Exception e) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ResponseModel.error(e.getMessage()));
    }


}
