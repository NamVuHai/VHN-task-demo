package com.assignment.task.manager.controllers.user;

import com.assignment.core.model.ResponseModel;
import com.assignment.task.manager.apis.user.*;
import com.assignment.task.manager.model.request.CommonRequest;
import com.assignment.task.manager.model.request.user.CreateUserRequest;
import com.assignment.task.manager.model.request.user.SearchUserRequest;
import com.assignment.task.manager.model.request.user.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserControllers {

    private final CreateUserApi createUserApi;
    private final UpdateUserApi updateUserApi;
    private final GetUserDetailApi getUserDetailApi;
    private final SearchUserApi searchUserApi;
    private final DeleteUserApi deleteUserApi;
    private final GetAllUserApi getAllUserApi;

    @PostMapping("/registration")
    public ResponseModel<?> registerUser(@RequestBody CreateUserRequest request) {
        return createUserApi.execute(request);
    }

    @PutMapping("/{id}")
    public ResponseModel<?> updateUser(@PathVariable String id, @RequestBody UpdateUserRequest request) {
        request.setUserId(id);
        return updateUserApi.execute(request);
    }

    @GetMapping("/{id}")
    public ResponseModel<?> getUser(@PathVariable String id) {
        return getUserDetailApi.execute(new CommonRequest(id));
    }

    @GetMapping("/search")
    public ResponseModel<?> searchUser(@RequestParam(required = false) String userName
            , @RequestParam(required = false) String userId
            , @RequestParam(required = false) String fullName
            , @RequestParam Integer page
            , @RequestParam Integer limit) {
        SearchUserRequest searchUserRequest = new SearchUserRequest(userId, userName, fullName);
        searchUserRequest.setPage(page);
        searchUserRequest.setLimit(limit);
        return searchUserApi.execute(searchUserRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseModel<?> deleteUser(@PathVariable String id) {
        return deleteUserApi.execute(new CommonRequest(id));
    }

    @GetMapping
    public ResponseModel<?> getAllUsers() {
        return getAllUserApi.execute(new CommonRequest());
    }
}

