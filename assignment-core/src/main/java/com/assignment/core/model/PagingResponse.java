package com.assignment.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PagingResponse <T> extends ResponseData{
    private PagingData<T> data;

    public static PagingResponse newResponse(int total, int currentPage, int limit, List list) {
        int totalPage = total % limit > 0 ? total / limit + 1 : total / limit;
        PagingMetaData metaData = new PagingMetaData(currentPage, limit, total, totalPage);
        return new PagingResponse<>(new PagingData<>(metaData, list));
    }

    public static PagingResponse emptyResponse() {
        return emptyResponse(1);
    }

    public static PagingResponse emptyResponse(int limit) {
        PagingMetaData metaData = new PagingMetaData(1, limit, 0, 0);
        return new PagingResponse<>(new PagingData<>(metaData, Collections.emptyList()));
    }

    public static PagingResponse newResponse(PagingRequest pagingRequest, PagedResult pagedResult) {
        return newResponse(pagedResult.getTotal(), pagingRequest.getPage(), pagingRequest.getLimit(), pagedResult.getResults());
    }

    public static PagingResponse newResponse(List list) {
        return newResponse(list.size(), 0, 1, list);
    }
}
