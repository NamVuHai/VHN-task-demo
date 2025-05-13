package com.assignment.task.manager.apis.task;



import com.assignment.core.constant.ErrorCode;
import com.assignment.core.exception.BusinessException;
import com.assignment.core.model.ResponseModel;
import com.assignment.core.utils.HttpRequestUtil;
import com.assignment.task.manager.constant.TaskStatus;
import com.assignment.task.manager.constant.TaskType;
import com.assignment.task.manager.entities.BugEntity;
import com.assignment.task.manager.entities.FeatureEntity;
import com.assignment.task.manager.entities.UserEntity;
import com.assignment.task.manager.model.request.task.CreateTaskRequest;
import com.assignment.task.manager.model.response.task.CreateTaskResponse;
import com.assignment.task.manager.services.TaskService;
import com.assignment.task.manager.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateTaskApiTest {

    @Mock
    private TaskService taskService;

    @Mock
    private UserService userService;

    @InjectMocks
    private CreateTaskApi createTaskApi;

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
    void execute_shouldCreateBugTaskWithUserSuccessfully() {
        // Arrange
        CreateTaskRequest request = new CreateTaskRequest();
        request.setType(TaskType.BUG);
        request.setName("Test Bug");
        request.setDescription("Bug description");
        request.setUserId("user-uuid-123");
        request.setSeverity("High");
        request.setStepsToReproduce("Steps to reproduce bug");

        UserEntity user = new UserEntity();
        user.setUserId("user-uuid-123");

        BugEntity savedTask = new BugEntity();
        savedTask.setId("task-uuid-456");
        savedTask.setName("Test Bug");
        savedTask.setDescription("Bug description");
        savedTask.setUser(user);
        savedTask.setStatus(TaskStatus.OPEN);
        savedTask.setType(TaskType.BUG);
        savedTask.setCreateBy("test-user");
        savedTask.setIsDeleted(false);
        savedTask.setSeverity("High");
        savedTask.setStepsToReproduce("Steps to reproduce bug");

        when(userService.getOrElseThrow("user-uuid-123")).thenReturn(user);
        when(taskService.save(any(BugEntity.class))).thenReturn(savedTask);

        // Act
        ResponseModel<CreateTaskResponse> response = createTaskApi.execute(request);

        // Assert
        assertNotNull(response);
        assertEquals("Task created successfully", response.getMessage());
        CreateTaskResponse responseData = response.getData();
        assertEquals("task-uuid-456", responseData.getId());
        assertEquals("user-uuid-123", responseData.getUserId());
        assertEquals(TaskType.BUG, responseData.getType());
        verify(userService).getOrElseThrow("user-uuid-123");
        verify(taskService).save(any(BugEntity.class));
    }

    @Test
    void execute_shouldCreateBugTaskWithoutUserSuccessfully() {
        // Arrange
        CreateTaskRequest request = new CreateTaskRequest();
        request.setType(TaskType.BUG);
        request.setName("Test Bug");
        request.setDescription("Bug description");
        request.setSeverity("High");
        request.setStepsToReproduce("Steps to reproduce bug");

        BugEntity savedTask = new BugEntity();
        savedTask.setId("task-uuid-456");
        savedTask.setName("Test Bug");
        savedTask.setDescription("Bug description");
        savedTask.setStatus(TaskStatus.OPEN);
        savedTask.setType(TaskType.BUG);
        savedTask.setCreateBy("test-user");
        savedTask.setIsDeleted(false);
        savedTask.setSeverity("High");
        savedTask.setStepsToReproduce("Steps to reproduce bug");

        when(taskService.save(any(BugEntity.class))).thenReturn(savedTask);

        // Act
        ResponseModel<CreateTaskResponse> response = createTaskApi.execute(request);

        // Assert
        assertNotNull(response);
        assertEquals("Task created successfully", response.getMessage());
        CreateTaskResponse responseData = response.getData();
        assertEquals("task-uuid-456", responseData.getId());
        assertNull(responseData.getUserId());
        assertEquals(TaskType.BUG, responseData.getType());
        verifyNoInteractions(userService);
        verify(taskService).save(any(BugEntity.class));
    }

    @Test
    void execute_shouldCreateFeatureTaskWithUserSuccessfully() {
        // Arrange
        CreateTaskRequest request = new CreateTaskRequest();
        request.setType(TaskType.FEATURE);
        request.setName("Test Feature");
        request.setDescription("Feature description");
        request.setUserId("user-uuid-123");
        request.setBusinessValue("High");
        request.setDeadline(LocalDate.of(2025, 12, 31));

        UserEntity user = new UserEntity();
        user.setUserId("user-uuid-123");

        FeatureEntity savedTask = new FeatureEntity();
        savedTask.setId("task-uuid-456");
        savedTask.setName("Test Feature");
        savedTask.setDescription("Feature description");
        savedTask.setUser(user);
        savedTask.setStatus(TaskStatus.OPEN);
        savedTask.setType(TaskType.FEATURE);
        savedTask.setCreateBy("test-user");
        savedTask.setIsDeleted(false);
        savedTask.setBusinessValue("High");
        savedTask.setDeadline(LocalDate.of(2025, 12, 31));

        when(userService.getOrElseThrow("user-uuid-123")).thenReturn(user);
        when(taskService.save(any(FeatureEntity.class))).thenReturn(savedTask);

        // Act
        ResponseModel<CreateTaskResponse> response = createTaskApi.execute(request);

        // Assert
        assertNotNull(response);
        assertEquals("Task created successfully", response.getMessage());
        CreateTaskResponse responseData = response.getData();
        assertEquals("task-uuid-456", responseData.getId());
        assertEquals("user-uuid-123", responseData.getUserId());
        assertEquals(TaskType.FEATURE, responseData.getType());
        verify(userService).getOrElseThrow("user-uuid-123");
        verify(taskService).save(any(FeatureEntity.class));
    }

    @Test
    void execute_shouldThrowException_whenTaskTypeIsInvalid() {
        // Arrange
        CreateTaskRequest request = new CreateTaskRequest();
        request.setType(null); // Invalid type
        request.setName("Test Task");
        request.setDescription("Task description");

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () -> createTaskApi.execute(request));
        assertEquals(ErrorCode.INVALID_REQUEST_PARAMETER, exception.getErrorCode());
        assertEquals("Invalid request parameter", exception.getMessage());
        verifyNoInteractions(userService, taskService);
    }

    @Test
    void execute_shouldThrowException_whenUserNotFound() {
        // Arrange
        CreateTaskRequest request = new CreateTaskRequest();
        request.setType(TaskType.BUG);
        request.setName("Test Bug");
        request.setDescription("Bug description");
        request.setUserId("user-uuid-123");
        request.setSeverity("High");
        request.setStepsToReproduce("Steps to reproduce bug");

        when(userService.getOrElseThrow("user-uuid-123")).thenThrow(new RuntimeException("User not found"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> createTaskApi.execute(request));
        assertEquals("User not found", exception.getMessage());
        verify(userService).getOrElseThrow("user-uuid-123");
        verifyNoInteractions(taskService);
    }
}