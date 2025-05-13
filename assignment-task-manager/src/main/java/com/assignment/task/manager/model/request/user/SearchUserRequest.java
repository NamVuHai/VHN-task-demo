package com.assignment.task.manager.model.request.user;

import com.assignment.core.model.PagingRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class SearchUserRequest extends PagingRequest {
    private String userId;
    private String userName;
    private String fullName;
}
