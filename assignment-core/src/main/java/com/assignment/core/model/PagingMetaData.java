package com.assignment.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PagingMetaData {
    private int pageNum;
    private int pageSize;
    private int total;
    private int totalPage;
}
