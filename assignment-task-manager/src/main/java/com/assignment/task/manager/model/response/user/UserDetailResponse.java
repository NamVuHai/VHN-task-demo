package com.assignment.task.manager.model.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailResponse {
    private String userId;
    private String userName;
    private String fullName;
    private LocalDateTime createAt;
}
