package com.assignment.task.manager.apis.task;

import com.assignment.core.common.CommonApi;
import com.assignment.core.model.ResponseModel;
import com.assignment.task.manager.constant.TaskType;
import com.assignment.task.manager.entities.BugEntity;
import com.assignment.task.manager.entities.FeatureEntity;
import com.assignment.task.manager.model.request.CommonRequest;
import com.assignment.task.manager.model.response.task.TaskDetailResponse;
import com.assignment.task.manager.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllTaskApi implements CommonApi<CommonRequest, ResponseModel<List<TaskDetailResponse>>> {
    private final TaskService taskService;

    @Override
    public ResponseModel<List<TaskDetailResponse>> execute(CommonRequest requestData) {
        List<TaskDetailResponse> result = taskService.getAll().stream().map(e -> {
            TaskDetailResponse taskDetailResponse = new TaskDetailResponse();
            taskDetailResponse.setId(e.getId());
            taskDetailResponse.setName(e.getName());
            taskDetailResponse.setDescription(e.getDescription());
            taskDetailResponse.setStatus(e.getStatus());
            taskDetailResponse.setType(e.getType());
            if(TaskType.BUG.equals(e.getType())) {
                BugEntity tmp = (BugEntity) e;
                taskDetailResponse.setSeverity(tmp.getSeverity());
                taskDetailResponse.setStepsToReproduce(tmp.getStepsToReproduce());
            }else if(TaskType.FEATURE.equals(e.getType())) {
                FeatureEntity tmp = (FeatureEntity) e;
                taskDetailResponse.setBusinessValue(tmp.getBusinessValue());
                taskDetailResponse.setDeadline(tmp.getDeadline());
            }
            return taskDetailResponse;
        }).toList();
        return ResponseModel.ok("Success", result);
    }
}
