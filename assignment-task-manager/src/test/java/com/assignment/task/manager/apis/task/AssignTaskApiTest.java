package com.assignment.task.manager.apis.task;

import com.assignment.core.model.ResponseModel;
import com.assignment.task.manager.entities.BugEntity;
import com.assignment.task.manager.entities.TaskEntity;
import com.assignment.task.manager.entities.UserEntity;
import com.assignment.task.manager.model.request.task.AssignTaskRequest;
import com.assignment.task.manager.model.response.CommonResponse;
import com.assignment.task.manager.services.TaskService;
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
class AssignTaskApiTest {

    @Mock
    private TaskService taskService;

    @Mock
    private UserService userService;

    @InjectMocks
    private AssignTaskApi assignTaskApi;

    private AssignTaskRequest request;
    private TaskEntity task;
    private UserEntity user;

    @BeforeEach
    void setUp() {
        request = new AssignTaskRequest();
        request.setTaskId("task-uuid-123");
        request.setUserId("user-uuid-456");

        task = new BugEntity();
        task.setId("task-uuid-123");

        user = new UserEntity();
        user.setUserId("user-uuid-456");
    }

    @Test
    void execute_shouldReturnSuccess_whenTaskAndUserExist() {
        // Arrange
        when(taskService.getOrElseThrow("task-uuid-123", "Task not found")).thenReturn(task);
        when(userService.getOrElseThrow("user-uuid-456", "User not found")).thenReturn(user);
        when(taskService.save(task)).thenReturn(task);

        // Act
        ResponseModel<CommonResponse> response = assignTaskApi.execute(request);

        // Assert
        assertNotNull(response);
        assertEquals("Assign Task success", response.getMessage());
        assertEquals(user, task.getUser());
        verify(taskService).getOrElseThrow("task-uuid-123", "Task not found");
        verify(userService).getOrElseThrow("user-uuid-456", "User not found");
        verify(taskService).save(task);
    }

    @Test
    void execute_shouldThrowException_whenTaskNotFound() {
        // Arrange
        when(taskService.getOrElseThrow("task-uuid-123", "Task not found"))
                .thenThrow(new RuntimeException("Task not found"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> assignTaskApi.execute(request));
        assertEquals("Task not found", exception.getMessage());
        verify(taskService).getOrElseThrow("task-uuid-123", "Task not found");
        verifyNoInteractions(userService);
        verifyNoMoreInteractions(taskService);
    }

    @Test
    void execute_shouldThrowException_whenUserNotFound() {
        // Arrange
        when(taskService.getOrElseThrow("task-uuid-123", "Task not found")).thenReturn(task);
        when(userService.getOrElseThrow("user-uuid-456", "User not found"))
                .thenThrow(new RuntimeException("User not found"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> assignTaskApi.execute(request));
        assertEquals("User not found", exception.getMessage());
        verify(taskService).getOrElseThrow("task-uuid-123", "Task not found");
        verify(userService).getOrElseThrow("user-uuid-456", "User not found");
        verifyNoMoreInteractions(taskService);
    }
}