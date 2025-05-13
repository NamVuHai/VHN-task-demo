package com.assignment.task.manager.model.request.task;

import com.assignment.core.model.PagingRequest;
import com.assignment.task.manager.constant.TaskStatus;
import com.assignment.task.manager.constant.TaskType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SearchTaskRequest extends PagingRequest {
    private String userId;
    private TaskType type;
    private TaskStatus status;
    private String otherText;
}
