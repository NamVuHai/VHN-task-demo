package com.assignment.task.manager.apis.user;

import com.assignment.core.common.CommonApi;
import com.assignment.core.model.PagingResponse;
import com.assignment.core.model.ResponseModel;
import com.assignment.task.manager.model.request.user.SearchUserRequest;
import com.assignment.task.manager.model.response.user.UserDetailResponse;
import com.assignment.task.manager.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchUserApi implements CommonApi<SearchUserRequest, ResponseModel<PagingResponse<UserDetailResponse>>> {

    private final UserService userService;

    @Override
    public ResponseModel<PagingResponse<UserDetailResponse>> execute(SearchUserRequest requestData) {
        if (requestData.getPage() == null || requestData.getPage() >=0) {
            requestData.setPage(1);
        }
        Pageable pageable = PageRequest.of(requestData.getPage() - 1, requestData.getLimit());
        return ResponseModel.ok("Successfully searched users", userService.searchUserDetail(requestData, pageable));
    }
}
