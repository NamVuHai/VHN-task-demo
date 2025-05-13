package com.assignment.task.manager.model.request.user;

import com.assignment.core.model.RequestData;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest extends RequestData {
    @NotBlank(message = "User Name is required")
    private String userName;
    @NotBlank(message = "Password is required")
    private String password;
    private String fullName;

}
