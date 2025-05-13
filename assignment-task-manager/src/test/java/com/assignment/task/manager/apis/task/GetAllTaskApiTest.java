package com.assignment.task.manager.apis.task;

import com.assignment.core.model.ResponseModel;
import com.assignment.task.manager.constant.TaskStatus;
import com.assignment.task.manager.constant.TaskType;
import com.assignment.task.manager.entities.BugEntity;
import com.assignment.task.manager.entities.FeatureEntity;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllTaskApiTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private GetAllTaskApi getAllTaskApi;

    private BugEntity bugTask;
    private FeatureEntity featureTask;

    @BeforeEach
    void setUp() {
        // Setup BugEntity
        bugTask = new BugEntity();
        bugTask.setId("bug-uuid-123");
        bugTask.setName("Test Bug");
        bugTask.setDescription("Bug description");
        bugTask.setStatus(TaskStatus.OPEN);
        bugTask.setType(TaskType.BUG);
        bugTask.setSeverity("High");
        bugTask.setStepsToReproduce("Steps to reproduce bug");

        // Setup FeatureEntity
        featureTask = new FeatureEntity();
        featureTask.setId("feature-uuid-456");
        featureTask.setName("Test Feature");
        featureTask.setDescription("Feature description");
        featureTask.setStatus(TaskStatus.IN_PROGRESS);
        featureTask.setType(TaskType.FEATURE);
        featureTask.setBusinessValue("High");
        featureTask.setDeadline(LocalDate.of(2025, 12, 31));
    }

    @Test
    void execute_shouldReturnTaskList_whenTasksExist() {
        // Arrange
        CommonRequest request = new CommonRequest();
        List<TaskEntity> tasks = Arrays.asList(bugTask, featureTask);
        when(taskService.getAll()).thenReturn(tasks);

        // Act
        ResponseModel<List<TaskDetailResponse>> response = getAllTaskApi.execute(request);

        // Assert
        assertNotNull(response);
        assertEquals("Success", response.getMessage());
        List<TaskDetailResponse> result = response.getData();
        assertEquals(2, result.size());

        // Verify Bug Task
        TaskDetailResponse bugResponse = result.get(0);
        assertEquals("bug-uuid-123", bugResponse.getId());
        assertEquals("Test Bug", bugResponse.getName());
        assertEquals("Bug description", bugResponse.getDescription());
        assertEquals(TaskStatus.OPEN, bugResponse.getStatus());
        assertEquals(TaskType.BUG, bugResponse.getType());
        assertEquals("High", bugResponse.getSeverity());
        assertEquals("Steps to reproduce bug", bugResponse.getStepsToReproduce());
        assertNull(bugResponse.getBusinessValue());
        assertNull(bugResponse.getDeadline());

        // Verify Feature Task
        TaskDetailResponse featureResponse = result.get(1);
        assertEquals("feature-uuid-456", featureResponse.getId());
        assertEquals("Test Feature", featureResponse.getName());
        assertEquals("Feature description", featureResponse.getDescription());
        assertEquals(TaskStatus.IN_PROGRESS, featureResponse.getStatus());
        assertEquals(TaskType.FEATURE, featureResponse.getType());
        assertEquals("High", featureResponse.getBusinessValue());
        assertEquals(LocalDate.of(2025, 12, 31), featureResponse.getDeadline());
        assertNull(featureResponse.getSeverity());
        assertNull(featureResponse.getStepsToReproduce());

        verify(taskService).getAll();
        verifyNoMoreInteractions(taskService);
    }

    @Test
    void execute_shouldReturnEmptyList_whenNoTasksExist() {
        // Arrange
        CommonRequest request = new CommonRequest();
        when(taskService.getAll()).thenReturn(Collections.emptyList());

        // Act
        ResponseModel<List<TaskDetailResponse>> response = getAllTaskApi.execute(request);

        // Assert
        assertNotNull(response);
        assertEquals("Success", response.getMessage());
        List<TaskDetailResponse> result = response.getData();
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(taskService).getAll();
        verifyNoMoreInteractions(taskService);
    }
}
