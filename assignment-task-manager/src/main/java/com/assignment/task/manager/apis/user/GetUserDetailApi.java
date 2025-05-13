package com.assignment.task.manager.apis.user;

import com.assignment.core.common.CommonApi;
import com.assignment.core.model.ResponseModel;
import com.assignment.task.manager.entities.UserEntity;
import com.assignment.task.manager.model.request.CommonRequest;
import com.assignment.task.manager.model.response.user.UserDetailResponse;
import com.assignment.task.manager.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUserDetailApi implements CommonApi<CommonRequest, ResponseModel<UserDetailResponse>> {

    private final UserService userService;

    @Override
    public ResponseModel<UserDetailResponse> execute(CommonRequest requestData) {
        UserEntity user = userService.getOrElseThrow(requestData.getData());
        UserDetailResponse userDetailResponse = new UserDetailResponse();
        BeanUtils.copyProperties(user, userDetailResponse);
        return ResponseModel.ok("success", userDetailResponse);
    }
}
