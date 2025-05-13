package com.assignment.task.manager.model.response.task;

import com.assignment.task.manager.constant.TaskStatus;
import com.assignment.task.manager.constant.TaskType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class TaskDetailResponse{
    private String id;
    private String name;
    private String description;
    private TaskStatus status;
    private TaskType type;
    private String severity;
    private String stepsToReproduce;
    private String businessValue;
    private LocalDate deadline;
}
