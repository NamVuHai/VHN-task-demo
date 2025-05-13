package com.assignment.task.manager.apis.task;

import com.assignment.core.model.ResponseModel;
import com.assignment.task.manager.constant.TaskStatus;
import com.assignment.task.manager.constant.TaskType;
import com.assignment.task.manager.entities.TaskEntity;
import com.assignment.task.manager.model.request.CommonRequest;
import com.assignment.task.manager.model.response.task.TaskDetailResponse;
import com.assignment.task.manager.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetTaskDetailApiTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private GetTaskDetailApi getTaskDetailApi;

    private TaskEntity task;
    private CommonRequest request;

    @BeforeEach
    void setUp() {
        // Setup TaskEntity
        task = new TaskEntity() {
            // Anonymous subclass to allow instantiation
        };
        task.setId("task-uuid-123");
        task.setName("Test Task");
        task.setDescription("Task description");
        task.setStatus(TaskStatus.OPEN);
        task.setType(TaskType.BUG);
        task.setCreateBy("test-user");
        task.setIsDeleted(false);

        // Setup CommonRequest
        request = new CommonRequest();
        request.setData("task-uuid-123");
    }

    @Test
    void execute_shouldReturnTaskDetail_whenTaskExists() {
        // Arrange
        when(taskService.getOrElseThrow("task-uuid-123", "Task not found")).thenReturn(task);

        // Act
        ResponseModel<TaskDetailResponse> response = getTaskDetailApi.execute(request);

        // Assert
        assertNotNull(response);
        assertEquals("Success", response.getMessage());
        TaskDetailResponse result = response.getData();
        assertNotNull(result);
        assertEquals("task-uuid-123", result.getId());
        assertEquals("Test Task", result.getName());
        assertEquals("Task description", result.getDescription());
        assertEquals(TaskStatus.OPEN, result.getStatus());
        assertEquals(TaskType.BUG, result.getType());
        verify(taskService).getOrElseThrow("task-uuid-123", "Task not found");
        verifyNoMoreInteractions(taskService);
    }

    @Test
    void execute_shouldThrowException_whenTaskNotFound() {
        // Arrange
        when(taskService.getOrElseThrow("task-uuid-123", "Task not found"))
                .thenThrow(new RuntimeException("Task not found"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> getTaskDetailApi.execute(request));
        assertEquals("Task not found", exception.getMessage());
        verify(taskService).getOrElseThrow("task-uuid-123", "Task not found");
        verifyNoMoreInteractions(taskService);
    }
}
