package com.assignment.task.manager.apis.task;

import com.assignment.core.common.CommonApi;
import com.assignment.core.model.ResponseModel;
import com.assignment.task.manager.model.request.CommonRequest;
import com.assignment.task.manager.model.response.CommonResponse;
import com.assignment.task.manager.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteTaskApi implements CommonApi<CommonRequest, ResponseModel<CommonResponse>> {
    private final TaskService taskService;

    @Override
    public ResponseModel<CommonResponse> execute(CommonRequest requestData) {
        taskService.deleteById(requestData.getData());
        return ResponseModel.ok("Success");
    }
}
