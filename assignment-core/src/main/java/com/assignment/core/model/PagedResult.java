package com.assignment.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PagedResult <T>{
    private int total;
    private List<T> results;

    public boolean isEmpty() {
        return CollectionUtils.isEmpty(results);
    }
}
