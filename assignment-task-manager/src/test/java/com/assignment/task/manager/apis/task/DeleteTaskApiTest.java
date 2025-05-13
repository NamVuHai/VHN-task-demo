package com.assignment.task.manager.apis.task;

import com.assignment.core.constant.ErrorCode;
import com.assignment.core.exception.BusinessException;
import com.assignment.core.model.ResponseModel;
import com.assignment.core.utils.HttpRequestUtil;
import com.assignment.task.manager.constant.TaskStatus;
import com.assignment.task.manager.constant.TaskType;
import com.assignment.task.manager.entities.BugEntity;
import com.assignment.task.manager.model.request.CommonRequest;
import com.assignment.task.manager.model.response.CommonResponse;
import com.assignment.task.manager.services.TaskService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteTaskApiTest {
    @Mock
    private TaskService taskService;

    @InjectMocks
    private DeleteTaskApi deleteTaskApi;

    private MockedStatic<HttpRequestUtil> httpRequestUtilMock;

    @BeforeEach
    void setUp() {
        httpRequestUtilMock = mockStatic(HttpRequestUtil.class);
        httpRequestUtilMock.when(HttpRequestUtil::getUserName).thenReturn("test-user");
    }

    @AfterEach
    void tearDown() {
        httpRequestUtilMock.close();
    }

    @Test
    void execute_shouldDeleteTaskSuccessfully() {
        // Arrange
        String taskId = "task-uuid-123";
        BugEntity bugEntity = new BugEntity();
        bugEntity.setId("task-uuid-456");
        bugEntity.setName("Test Bug");
        bugEntity.setDescription("Bug description");
        bugEntity.setStatus(TaskStatus.OPEN);
        bugEntity.setType(TaskType.BUG);
        bugEntity.setCreateBy("test-user");
        bugEntity.setIsDeleted(false);
        bugEntity.setSeverity("High");
        bugEntity.setStepsToReproduce("Steps to reproduce bug");

        httpRequestUtilMock.when(HttpRequestUtil::getUserName).thenReturn("test-user");

        // Act
        when(taskService.deleteById(taskId)).thenReturn(bugEntity);

        ResponseModel<CommonResponse> responseModel = deleteTaskApi.execute(new CommonRequest(taskId));


        // Assert
        verify(taskService).deleteById(taskId);
        assertEquals("Success", responseModel.getMessage());
    }

    @Test
    void execute_shouldThrowException_whenTaskNotFound() {
        // Arrange
        String taskId = "task-uuid-123";
        when(taskService.deleteById(taskId)).thenThrow(new BusinessException(ErrorCode.ENTITY_NOT_FOUND,"Task not found"));
        // Act & Assert
        RuntimeException exception = assertThrows(BusinessException.class, () -> deleteTaskApi.execute(new CommonRequest(taskId)));
        assertEquals("Task not found", exception.getMessage());
        verify(taskService).deleteById(taskId);
    }
}
