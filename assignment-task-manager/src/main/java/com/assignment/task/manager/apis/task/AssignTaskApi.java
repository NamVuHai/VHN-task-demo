package com.assignment.task.manager.apis.task;

import com.assignment.core.common.CommonApi;
import com.assignment.core.model.ResponseModel;
import com.assignment.task.manager.entities.TaskEntity;
import com.assignment.task.manager.entities.UserEntity;
import com.assignment.task.manager.model.request.task.AssignTaskRequest;
import com.assignment.task.manager.model.response.CommonResponse;
import com.assignment.task.manager.services.TaskService;
import com.assignment.task.manager.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssignTaskApi implements CommonApi<AssignTaskRequest, ResponseModel<CommonResponse>> {
    private final TaskService taskService;
    private final UserService userService;

    @Override
    public ResponseModel<CommonResponse> execute(AssignTaskRequest requestData) {
        TaskEntity task = taskService.getOrElseThrow(requestData.getTaskId(),"Task not found");
        UserEntity user = userService.getOrElseThrow(requestData.getUserId(),"User not found");
        task.setUser(user);
        taskService.save(task);
        return ResponseModel.ok("Assign Task success");
    }
}
