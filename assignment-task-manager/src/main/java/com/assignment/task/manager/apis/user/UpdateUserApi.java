package com.assignment.task.manager.apis.user;

import com.assignment.core.common.CommonApi;
import com.assignment.core.model.ResponseModel;
import com.assignment.task.manager.entities.UserEntity;
import com.assignment.task.manager.model.request.user.UpdateUserRequest;
import com.assignment.task.manager.model.response.CommonResponse;
import com.assignment.task.manager.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateUserApi implements CommonApi<UpdateUserRequest, ResponseModel<CommonResponse>> {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseModel<CommonResponse> execute(UpdateUserRequest requestData) {
        UserEntity user = userService.getOrElseThrow(requestData.getUserId());
        String currentPassword = user.getPassword();
        BeanUtils.copyProperties(requestData, user);
        if(!passwordEncoder.matches(currentPassword, requestData.getPassword())) {
            user.setPassword(passwordEncoder.encode(requestData.getPassword()));
        }
        userService.save(user);
        return ResponseModel.ok("User updated successfully");
    }
}
