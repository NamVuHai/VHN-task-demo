package com.assignment.task.manager.model.request.user;

import com.assignment.core.model.RequestData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest extends RequestData {
    @JsonIgnore
    private String userId;
    private String userName;
    private String password;
    private String fullName;

}
