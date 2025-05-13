package com.assignment.auth.controllers;

import com.assignment.auth.api.LoginApi;
import com.assignment.auth.api.RefreshTokenApi;
import com.assignment.auth.model.request.RefreshTokenRequest;
import com.assignment.auth.model.request.UserLoginRequest;
import com.assignment.auth.model.response.LoginResponse;
import com.assignment.core.model.ResponseModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final LoginApi loginApi;
    private final RefreshTokenApi refreshTokenApi;

    @PostMapping("/login")
    public ResponseModel<LoginResponse> login(@Valid @RequestBody UserLoginRequest request) {
        return loginApi.execute(request);
    }

    @PostMapping("/refresh-token")
    public ResponseModel<LoginResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return refreshTokenApi.execute(request);
    }
}
