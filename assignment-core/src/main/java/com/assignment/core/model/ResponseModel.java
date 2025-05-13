package com.assignment.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseModel<T> extends ResponseData{
    private boolean success;
    private HttpStatus code;
    private String message;
    private T data;

    private ResponseModel(boolean success, HttpStatus code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseModel<T> ok (String message , T data){
        return new ResponseModel<>(true, HttpStatus.OK, message,data);
    }

    public static <T> ResponseModel<T> ok(String message) {
        return new ResponseModel<>(true, HttpStatus.OK, message, null);
    }


    public static <T> ResponseModel<T> error(String message,HttpStatus code, T data) {
        return new ResponseModel<>(false,code , message, data);
    }

    public static <T> ResponseModel<T> error(String message) {
        return error(message, HttpStatus.BAD_REQUEST,null);
    }

}
