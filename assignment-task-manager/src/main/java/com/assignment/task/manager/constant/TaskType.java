package com.assignment.task.manager.constant;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TaskType {
    BUG("bug"),
    FEATURE("feature");

    private final String value;

    private TaskType(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
    public static TaskType fromValue(String value) {
        for (TaskType taskType : TaskType.values()) {
            if (taskType.value.equals(value)) {
                return taskType;
            }
        }
        return null;
    }

    @JsonCreator
    public static TaskType getTaskType(String value) {
        for (TaskType taskType : TaskType.values()) {
            if (taskType.getValue().equalsIgnoreCase(value)) {
                return taskType;
            }
        }
        throw new IllegalArgumentException("Invalid TaskType: " + value);
    }

}
