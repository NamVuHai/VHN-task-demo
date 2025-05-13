package com.assignment.task.manager.apis.user;

import com.assignment.core.common.CommonApi;
import com.assignment.core.model.ResponseModel;
import com.assignment.task.manager.entities.UserEntity;
import com.assignment.task.manager.model.request.user.CreateUserRequest;
import com.assignment.task.manager.model.response.CommonResponse;
import com.assignment.task.manager.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserApi implements CommonApi<CreateUserRequest, ResponseModel<CommonResponse>> {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseModel<CommonResponse> execute(CreateUserRequest requestData) {
        requestData.setPassword(passwordEncoder.encode(requestData.getPassword()));
        UserEntity e = new UserEntity();
        BeanUtils.copyProperties(requestData, e);
        e.setIsDeleted(false);
        e = userService.save(e);
        return ResponseModel.ok(e.getUserId());
    }
}
