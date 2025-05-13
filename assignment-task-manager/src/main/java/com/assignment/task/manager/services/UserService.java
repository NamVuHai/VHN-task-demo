package com.assignment.task.manager.services;

import com.assignment.core.common.CommonService;
import com.assignment.core.model.PagingResponse;
import com.assignment.core.utils.HttpRequestUtil;
import com.assignment.task.manager.entities.UserEntity;
import com.assignment.task.manager.model.request.user.SearchUserRequest;
import com.assignment.task.manager.model.response.user.UserDetailResponse;
import com.assignment.task.manager.repositories.UserRepository;
import com.assignment.task.manager.repositories.custom.UserRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService extends CommonService<UserEntity, String, UserRepository> {

    private final UserRepositoryCustom userRepositoryCustom;

    protected UserService(UserRepository repo, UserRepositoryCustom userRepositoryCustom) {
        super(repo);
        this.userRepositoryCustom = userRepositoryCustom;
    }

    public PagingResponse<UserDetailResponse> searchUserDetail(SearchUserRequest request, Pageable pageable) {
        return userRepositoryCustom.searchUserDetail(request, pageable);
    }

    public List<UserEntity> getAllUserDetails() {
        return repo.findAll();
    }

    @Override
    public UserEntity delete(UserEntity entity) {
        entity.setIsDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        entity.setDeletedBy(HttpRequestUtil.getUserName());
        return repo.save(entity);
    }

    @Override
    public UserEntity deleteById(String s) {
        UserEntity e = getOrElseThrow(s, "User not found");
        return delete(e);
    }

    @Override
    protected String notFoundMessage() {
        return "User not found";
    }
}

