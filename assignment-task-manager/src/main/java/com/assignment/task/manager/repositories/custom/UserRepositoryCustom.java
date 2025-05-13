package com.assignment.task.manager.repositories.custom;

import com.assignment.core.model.PagingResponse;
import com.assignment.task.manager.model.request.user.SearchUserRequest;
import com.assignment.task.manager.model.response.user.UserDetailResponse;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryCustom {

    PagingResponse<UserDetailResponse> searchUserDetail(SearchUserRequest request, Pageable pageable);
}
