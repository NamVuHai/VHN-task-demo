package com.assignment.core.constant;

public enum ErrorCode {
    INVALID_REQUEST_PARAMETER,
    METHOD_NOT_ALLOWED,
    INTERNAL_SERVER_ERROR,
    RESOURCE_NOT_FOUND,
    CONFLICT,
    INVALID_EXECUTE,
    ENTITY_NOT_FOUND,

    ;

    private String description;

    private ErrorCode(String description) {
        this.description = description;
    }

    private ErrorCode() {
    }

    public String getDescription() {
        return this.description;
    }
}

