package com.assignment.task.manager.apis.task;

import com.assignment.core.common.CommonApi;
import com.assignment.core.model.ResponseModel;
import com.assignment.core.utils.HttpRequestUtil;
import com.assignment.task.manager.constant.TaskType;
import com.assignment.task.manager.entities.BugEntity;
import com.assignment.task.manager.entities.FeatureEntity;
import com.assignment.task.manager.entities.TaskEntity;
import com.assignment.task.manager.entities.UserEntity;
import com.assignment.task.manager.model.request.task.UpdateTaskRequest;
import com.assignment.task.manager.model.response.CommonResponse;
import com.assignment.task.manager.services.TaskService;
import com.assignment.task.manager.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UpdateTaskApi implements CommonApi<UpdateTaskRequest, ResponseModel<CommonResponse>> {

    private final TaskService taskService;
    private final UserService userService;
    @Override
    public ResponseModel<CommonResponse> execute(UpdateTaskRequest requestData) {
        TaskEntity task = taskService.getOrElseThrow(requestData.getTaskId());
        if(Objects.nonNull(requestData.getUserId())){
            UserEntity user = userService.getOrElseThrow(requestData.getUserId());
            task.setUser(user);
        }
        task.setName(requestData.getName());
        task.setDescription(requestData.getDescription());
        task.setUpdateAt(LocalDateTime.now());
        task.setUpdateBy(HttpRequestUtil.getUserName());
        if(TaskType.FEATURE.equals(task.getType())){
            FeatureEntity featureEntity = (FeatureEntity) task;
            featureEntity.setBusinessValue(requestData.getBusinessValue());
            featureEntity.setDeadline(requestData.getDeadline());
            taskService.save(featureEntity);
        }else if(TaskType.BUG.equals(task.getType())){
            BugEntity bugEntity = (BugEntity) task;
            bugEntity.setStepsToReproduce(requestData.getStepsToReproduce());
            bugEntity.setSeverity(requestData.getSeverity());
            taskService.save(bugEntity);
        }
        return ResponseModel.ok("Update task successfully");
    }
}
