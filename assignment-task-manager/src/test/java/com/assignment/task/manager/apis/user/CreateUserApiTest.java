package com.assignment.task.manager.apis.user;

import com.assignment.core.model.ResponseModel;
import com.assignment.task.manager.entities.UserEntity;
import com.assignment.task.manager.model.request.user.CreateUserRequest;
import com.assignment.task.manager.model.response.CommonResponse;
import com.assignment.task.manager.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserApiTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateUserApi createUserApi;

    private CreateUserRequest request;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        // Setup CreateUserRequest
        request = new CreateUserRequest();
        request.setUserName("testuser");
        request.setPassword("rawPassword");
        request.setFullName("First Name TEST");

        // Setup UserEntity
        userEntity = new UserEntity();
        userEntity.setUserId("user-uuid-123");
        userEntity.setUserName("testuser");
        userEntity.setPassword("encodedPassword");
        userEntity.setFullName("First Name TEST");
        userEntity.setIsDeleted(false);
    }

    @Test
    void execute_shouldCreateUserSuccessfully_whenValidRequest() {
        // Arrange
        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");
        when(userService.save(any(UserEntity.class))).thenReturn(userEntity);

        // Act
        ResponseModel<CommonResponse> response = createUserApi.execute(request);

        // Assert
        assertNotNull(response);
        verify(passwordEncoder).encode("rawPassword");
        verifyNoMoreInteractions(passwordEncoder, userService);
    }

    @Test
    void execute_shouldHandleNullPassword_whenPasswordIsNull() {
        // Arrange
        request.setPassword(null);
        when(passwordEncoder.encode(null)).thenReturn(null);
        when(userService.save(any(UserEntity.class))).thenReturn(userEntity);

        // Act
        ResponseModel<CommonResponse> response = createUserApi.execute(request);

        // Assert
        assertNotNull(response);
        verifyNoMoreInteractions(passwordEncoder, userService);
    }
}