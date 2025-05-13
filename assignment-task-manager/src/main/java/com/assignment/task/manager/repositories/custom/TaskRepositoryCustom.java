package com.assignment.task.manager.repositories.custom;

import com.assignment.core.model.PagingResponse;
import com.assignment.task.manager.model.request.task.SearchTaskRequest;
import com.assignment.task.manager.model.response.task.SearchTaskResponse;
import org.springframework.data.domain.Pageable;

public interface TaskRepositoryCustom {
    PagingResponse<SearchTaskResponse> search(SearchTaskRequest searchTaskRequest, Pageable pageable);
}
