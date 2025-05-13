package com.assignment.task.manager.model.request.task;

import com.assignment.core.model.RequestData;
import com.assignment.task.manager.constant.TaskType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false)
public class CreateTaskRequest extends RequestData {
    private TaskType type; // "BUG" or "FEATURE"
    private String name;
    private String userId;
    private String severity;
    private String stepsToReproduce;
    private String businessValue;
    private LocalDate deadline;
    private String description;

}
