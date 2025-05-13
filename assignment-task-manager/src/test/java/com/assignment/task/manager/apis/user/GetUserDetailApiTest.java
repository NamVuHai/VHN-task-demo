package com.assignment.task.manager.apis.user;

import com.assignment.core.model.ResponseModel;
import com.assignment.task.manager.entities.UserEntity;
import com.assignment.task.manager.model.request.CommonRequest;
import com.assignment.task.manager.model.response.user.UserDetailResponse;
import com.assignment.task.manager.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUserDetailApiTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private GetUserDetailApi getUserDetailApi;

    private CommonRequest request;
    private UserEntity user;

    @BeforeEach
    void setUp() {
        // Setup CommonRequest
        request = new CommonRequest();
        request.setData("user-uuid-123");

        // Setup UserEntity
        user = new UserEntity();
        user.setUserId("user-uuid-123");
        user.setUserName("testuser");
        user.setPassword("encodedPassword");
        user.setIsDeleted(false);
    }

    @Test
    void execute_shouldReturnUserDetail_whenUserExists() {
        // Arrange
        when(userService.getOrElseThrow("user-uuid-123")).thenReturn(user);

        // Act
        ResponseModel<UserDetailResponse> response = getUserDetailApi.execute(request);

        // Assert
        assertNotNull(response);
        assertEquals("success", response.getMessage());
        UserDetailResponse result = response.getData();
        assertNotNull(result);
        assertEquals("user-uuid-123", result.getUserId());
        assertEquals("testuser", result.getUserName());
        verify(userService).getOrElseThrow("user-uuid-123");
        verifyNoMoreInteractions(userService);
    }

    @Test
    void execute_shouldThrowException_whenUserNotFound() {
        // Arrange
        when(userService.getOrElseThrow("user-uuid-123")).thenThrow(new RuntimeException("User not found"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> getUserDetailApi.execute(request));
        assertEquals("User not found", exception.getMessage());
        verify(userService).getOrElseThrow("user-uuid-123");
        verifyNoMoreInteractions(userService);
    }
}
