package com.assignment.auth.api;

import com.assignment.auth.model.request.RefreshTokenRequest;
import com.assignment.auth.model.response.LoginResponse;
import com.assignment.auth.utils.JwtTokenUtil;
import com.assignment.core.common.CommonApi;
import com.assignment.core.constant.ErrorCode;
import com.assignment.core.exception.BusinessException;
import com.assignment.core.model.ResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class RefreshTokenApi implements CommonApi<RefreshTokenRequest, ResponseModel<LoginResponse>> {
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public ResponseModel<LoginResponse> execute(RefreshTokenRequest request) {
        if (jwtTokenUtil.validateRefreshToken(request.getRefreshToken())) {
            String jwtToken = jwtTokenUtil.generateToken(request.getRefreshToken());
            String userId = jwtTokenUtil.getUserIdFromToken(jwtToken);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            Date date = new Date(System.currentTimeMillis() + JwtTokenUtil.JWT_TOKEN_VALIDITY);
            LoginResponse response = new LoginResponse(jwtToken, request.getRefreshToken(), userId, formatter.format(date));
            return ResponseModel.ok("Refresh Token Success", response);
        }
        throw new BusinessException("Refresh token invalid");
    }
}
