package com.assignment.task.manager.model.response.task;

import com.assignment.task.manager.constant.TaskStatus;
import com.assignment.task.manager.constant.TaskType;
import lombok.Data;

@Data
public class SearchTaskResponse {
    private String userId;
    private String id;
    private String name;
    private String description;
    private TaskStatus status;
    private TaskType type;
}
