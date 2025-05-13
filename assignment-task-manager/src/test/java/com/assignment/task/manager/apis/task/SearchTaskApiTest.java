package com.assignment.task.manager.apis.task;

import com.assignment.core.model.PagingData;
import com.assignment.core.model.PagingMetaData;
import com.assignment.core.model.PagingResponse;
import com.assignment.core.model.ResponseModel;
import com.assignment.task.manager.model.request.task.SearchTaskRequest;
import com.assignment.task.manager.model.response.task.SearchTaskResponse;
import com.assignment.task.manager.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchTaskApiTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private SearchTaskApi searchTaskApi;

    private SearchTaskRequest request;
    private PagingResponse<SearchTaskResponse> pagingResponse;

    @BeforeEach
    void setUp() {
        // Setup SearchTaskRequest
        request = new SearchTaskRequest();
        request.setPage(2);
        request.setLimit(10);

        // Setup PagingResponse
        SearchTaskResponse response1 = new SearchTaskResponse();
        response1.setId("task-uuid-123");
        response1.setName("Test Task 1");

        SearchTaskResponse response2 = new SearchTaskResponse();
        response2.setId("task-uuid-456");
        response2.setName("Test Task 2");

        List<SearchTaskResponse> content = Arrays.asList(response1, response2);
        pagingResponse = new PagingResponse<>();
        PagingData<SearchTaskResponse> pagingData = new PagingData<>();
        pagingData.setItems(content);
        PagingMetaData metaData = new PagingMetaData(2, 10, 50, 5);
        pagingData.setMeta(metaData);
        pagingResponse.setData(pagingData);
    }

    @Test
    void execute_shouldReturnPagedTasks_whenValidPageAndLimit() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10); // page 2 - 1 = 1
        when(taskService.searchTasks(request, pageable)).thenReturn(pagingResponse);

        // Act
        ResponseModel<PagingResponse<SearchTaskResponse>> response = searchTaskApi.execute(request);

        // Assert
        assertNotNull(response);
        assertEquals("Successfully searched tasks", response.getMessage());
        PagingResponse<SearchTaskResponse> result = response.getData();
        assertNotNull(result);
        assertEquals(2, result.getData().getItems().size());
        assertEquals("task-uuid-123", result.getData().getItems().get(0).getId());
        assertEquals("task-uuid-456", result.getData().getItems().get(1).getId());
        assertEquals(5, result.getData().getMeta().getTotalPage());
        assertEquals(50L, result.getData().getMeta().getTotal());
        verify(taskService).searchTasks(request, pageable);
        verifyNoMoreInteractions(taskService);
    }

    @Test
    void execute_shouldSetDefaultPage_whenPageIsNull() {
        // Arrange
        request.setPage(null);
        Pageable pageable = PageRequest.of(0, 10); // page 1 - 1 = 0
        when(taskService.searchTasks(request, pageable)).thenReturn(pagingResponse);

        // Act
        ResponseModel<PagingResponse<SearchTaskResponse>> response = searchTaskApi.execute(request);

        // Assert
        assertNotNull(response);
        assertEquals("Successfully searched tasks", response.getMessage());
        assertEquals(1, request.getPage()); // Verify page was set to 1
        PagingResponse<SearchTaskResponse> result = response.getData();
        assertEquals(2, result.getData().getItems().size());
        verify(taskService).searchTasks(request, pageable);
        verifyNoMoreInteractions(taskService);
    }


}
