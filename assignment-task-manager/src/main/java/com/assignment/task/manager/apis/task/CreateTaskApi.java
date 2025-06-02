package com.assignment.task.manager.apis.task;

import com.assignment.core.common.CommonApi;
import com.assignment.core.constant.ErrorCode;
import com.assignment.core.exception.BusinessException;
import com.assignment.core.model.ResponseModel;
import com.assignment.core.utils.HttpRequestUtil;
import com.assignment.task.manager.constant.TaskStatus;
import com.assignment.task.manager.constant.TaskType;
import com.assignment.task.manager.entities.BugEntity;
import com.assignment.task.manager.entities.FeatureEntity;
import com.assignment.task.manager.entities.TaskEntity;
import com.assignment.task.manager.entities.UserEntity;
import com.assignment.task.manager.model.request.task.CreateTaskRequest;
import com.assignment.task.manager.model.response.task.CreateTaskResponse;
import com.assignment.task.manager.services.TaskService;
import com.assignment.task.manager.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CreateTaskApi implements CommonApi<CreateTaskRequest, ResponseModel<CreateTaskResponse>> {
    private final TaskService taskService;
    private final UserService userService;
    @Override
    public ResponseModel<CreateTaskResponse> execute(CreateTaskRequest requestData) {
        UserEntity user = null;
        if (Objects.nonNull(requestData.getUserId())){
            user = userService.getOrElseThrow(requestData.getUserId());
        }
        TaskEntity task = null;
        if(TaskType.BUG.equals(requestData.getType())){
            BugEntity bug = new BugEntity();
            bug.setSeverity(requestData.getSeverity());
            bug.setStepsToReproduce(requestData.getStepsToReproduce());
            task = bug;
        }else if (TaskType.FEATURE.equals(requestData.getType())){
            FeatureEntity feature = new FeatureEntity();
            feature.setBusinessValue(requestData.getBusinessValue());
            feature.setDeadline(requestData.getDeadline());
            task = feature;
        }else{
            throw new BusinessException("Invalid request parameter");
        }
        task.setUser(user);
        task.setName(requestData.getName());
        task.setDescription(requestData.getDescription());
        task.setCreateBy(HttpRequestUtil.getUserName());
        task.setIsDeleted(false);
        task.setStatus(TaskStatus.OPEN);
        task.setType(requestData.getType());
        TaskEntity saved = taskService.save(task);
        CreateTaskResponse createTaskResponse = new CreateTaskResponse();
        BeanUtils.copyProperties(saved, createTaskResponse);
        createTaskResponse.setUserId(requestData.getUserId());
        return ResponseModel.ok("Task created successfully", createTaskResponse);
    }
}
