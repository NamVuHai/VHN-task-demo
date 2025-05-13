package com.assignment.auth.api;

import com.assignment.auth.entities.UserEntity;
import com.assignment.auth.model.UserDetailCustom;
import com.assignment.auth.model.request.UserLoginRequest;
import com.assignment.auth.model.response.LoginResponse;
import com.assignment.auth.services.UserService;
import com.assignment.auth.utils.JwtTokenUtil;
import com.assignment.core.common.CommonApi;
import com.assignment.core.constant.ErrorCode;
import com.assignment.core.exception.BusinessException;
import com.assignment.core.model.ResponseModel;
import com.assignment.core.utils.SimpleLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class LoginApi implements CommonApi<UserLoginRequest, ResponseModel<LoginResponse>> {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseModel<LoginResponse> execute(UserLoginRequest request) {
        UserEntity user = userService.getUserByUserName(request.getUserName());
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.CONFLICT, "User name or password invalid");
        }
        if (Objects.nonNull(user.getIsDeleted()) && Boolean.TRUE.equals(user.getIsDeleted())) {
            throw new BusinessException(ErrorCode.CONFLICT, "User name or password invalid");
        }
        UserDetailCustom userDetails = new UserDetailCustom(user);
        String jwtToken = jwtTokenUtil.generateToken(userDetails);
        String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);
        SimpleLogger.info("start storing token to redis");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Date date = new Date(System.currentTimeMillis() + JwtTokenUtil.JWT_TOKEN_VALIDITY);

        LoginResponse loginResponse = new LoginResponse(jwtToken, refreshToken, user.getUserId(), formatter.format(date));
        return ResponseModel.ok("SUCCESS", loginResponse);
    }
}
