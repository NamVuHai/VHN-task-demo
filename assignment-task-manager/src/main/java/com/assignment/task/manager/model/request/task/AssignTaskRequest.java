package com.assignment.task.manager.model.request.task;

import com.assignment.core.model.RequestData;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AssignTaskRequest extends RequestData {
    private String taskId;
    private String userId;
}
