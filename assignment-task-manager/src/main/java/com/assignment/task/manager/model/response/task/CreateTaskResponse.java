package com.assignment.task.manager.model.response.task;

import com.assignment.task.manager.constant.TaskStatus;
import com.assignment.task.manager.constant.TaskType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateTaskResponse {
    private String id;
    private TaskType type;
    private String name;
    private String userId;
    private String severity;
    private String stepsToReproduce;
    private String description;
    private TaskStatus status;
    private LocalDate deadline;
    private String businessValue;
}
