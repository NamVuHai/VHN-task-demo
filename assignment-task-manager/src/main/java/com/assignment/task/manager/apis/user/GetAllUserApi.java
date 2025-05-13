package com.assignment.task.manager.apis.user;

import com.assignment.core.common.CommonApi;
import com.assignment.core.model.ResponseModel;
import com.assignment.task.manager.model.request.CommonRequest;
import com.assignment.task.manager.model.response.user.UserDetailResponse;
import com.assignment.task.manager.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllUserApi implements CommonApi<CommonRequest, ResponseModel<List<UserDetailResponse>>> {

    private final UserService userService;

    @Override
    public ResponseModel<List<UserDetailResponse>> execute(CommonRequest requestData) {
        List<UserDetailResponse> result = userService.getAllUserDetails().stream().map(e -> {
            UserDetailResponse userDetailResponse = new UserDetailResponse();
            userDetailResponse.setUserId(e.getUserId());
            userDetailResponse.setUserName(e.getUserName());
            userDetailResponse.setFullName(e.getFullName());
            userDetailResponse.setCreateAt(e.getCreateAt());
            return userDetailResponse;
        }).toList();
        return ResponseModel.ok("Success", result);
    }
}
