package com.assignment.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PagingRequest extends RequestData{
    private Integer limit = 20;
    private Integer page = 1;

    @JsonIgnore
    public int getOffset() {
        return (page - 1) * limit;
    }
}
