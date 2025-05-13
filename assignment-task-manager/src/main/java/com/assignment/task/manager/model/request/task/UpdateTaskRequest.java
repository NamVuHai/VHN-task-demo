package com.assignment.task.manager.model.request.task;

import com.assignment.core.model.RequestData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false)
public class UpdateTaskRequest  extends RequestData {
    private String taskId;
    private String name;
    private String userId;
    private String severity;
    private String stepsToReproduce;
    private String businessValue;
    private LocalDate deadline;
    private String description;
}
