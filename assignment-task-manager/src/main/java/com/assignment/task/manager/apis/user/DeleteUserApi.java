package com.assignment.task.manager.apis.user;

import com.assignment.core.common.CommonApi;
import com.assignment.core.model.ResponseModel;
import com.assignment.task.manager.model.request.CommonRequest;
import com.assignment.task.manager.model.response.CommonResponse;
import com.assignment.task.manager.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteUserApi implements CommonApi<CommonRequest, ResponseModel<CommonResponse>> {
    private final UserService userService;
    @Override
    public ResponseModel<CommonResponse> execute(CommonRequest requestData) {
        userService.deleteById(requestData.getData());
        return ResponseModel.ok("Delete Success");
    }
}
