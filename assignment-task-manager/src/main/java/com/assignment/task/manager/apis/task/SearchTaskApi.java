package com.assignment.task.manager.apis.task;

import com.assignment.core.common.CommonApi;
import com.assignment.core.model.PagingResponse;
import com.assignment.core.model.ResponseModel;
import com.assignment.task.manager.model.request.task.SearchTaskRequest;
import com.assignment.task.manager.model.response.task.SearchTaskResponse;
import com.assignment.task.manager.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchTaskApi implements CommonApi<SearchTaskRequest, ResponseModel<PagingResponse<SearchTaskResponse>>> {

    private final TaskService taskService;

    @Override
    public ResponseModel<PagingResponse<SearchTaskResponse>> execute(SearchTaskRequest requestData) {
        if (requestData.getPage() == null || requestData.getPage() >= 0) {
            requestData.setPage(1);
        }
        Pageable pageable = PageRequest.of(requestData.getPage() - 1, requestData.getLimit());
        return ResponseModel.ok("Successfully searched tasks", taskService.searchTasks(requestData, pageable));
    }
}