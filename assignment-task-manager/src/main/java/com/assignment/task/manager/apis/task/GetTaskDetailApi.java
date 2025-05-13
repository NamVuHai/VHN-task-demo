package com.assignment.task.manager.apis.task;

import com.assignment.core.common.CommonApi;
import com.assignment.core.model.ResponseModel;
import com.assignment.task.manager.entities.TaskEntity;
import com.assignment.task.manager.model.request.CommonRequest;
import com.assignment.task.manager.model.response.task.TaskDetailResponse;
import com.assignment.task.manager.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetTaskDetailApi implements CommonApi<CommonRequest, ResponseModel<TaskDetailResponse>> {
    private final TaskService taskService;

    @Override
    public ResponseModel<TaskDetailResponse> execute(CommonRequest requestData) {
        TaskEntity task = taskService.getOrElseThrow(requestData.getData(),"Task not found");
        TaskDetailResponse taskDetailResponse = new TaskDetailResponse();
        BeanUtils.copyProperties(task,taskDetailResponse);
        return ResponseModel.ok("Success", taskDetailResponse);
    }
}

