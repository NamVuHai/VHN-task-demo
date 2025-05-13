package com.assignment.task.manager.controllers.task;

import com.assignment.core.model.ResponseModel;
import com.assignment.task.manager.apis.task.*;
import com.assignment.task.manager.constant.TaskStatus;
import com.assignment.task.manager.constant.TaskType;
import com.assignment.task.manager.model.request.CommonRequest;
import com.assignment.task.manager.model.request.task.AssignTaskRequest;
import com.assignment.task.manager.model.request.task.CreateTaskRequest;
import com.assignment.task.manager.model.request.task.SearchTaskRequest;
import com.assignment.task.manager.model.request.task.UpdateTaskRequest;
import com.assignment.task.manager.model.response.task.CreateTaskResponse;
import com.assignment.task.manager.model.response.task.TaskDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskControllers {

    private final CreateTaskApi createTaskApi;
    private final UpdateTaskApi updateTaskApi;
    private final DeleteTaskApi deleteTaskApi;
    private final GetAllTaskApi getAllTaskApi;
    private final GetTaskDetailApi taskDetailApi;
    private final SearchTaskApi searchTaskApi;
    private final AssignTaskApi assignTaskApi;

    @PostMapping()
    public ResponseModel<CreateTaskResponse> createTask(@RequestBody CreateTaskRequest createTaskRequest) {
        return createTaskApi.execute(createTaskRequest);
    }

    @PutMapping("/{id}")
    public ResponseModel<?> updateTask(@PathVariable String id, @RequestBody UpdateTaskRequest updateTaskRequest) {
        updateTaskRequest.setTaskId(id);
        return updateTaskApi.execute(updateTaskRequest);
    }

    @GetMapping("/{id}")
    public ResponseModel<TaskDetailResponse> getTask(@PathVariable String id) {
        return taskDetailApi.execute(new CommonRequest(id));
    }

    @DeleteMapping("/{id}")
    public ResponseModel<?> deleteTask(@PathVariable String id) {
        return deleteTaskApi.execute(new CommonRequest(id));
    }

    @GetMapping
    public ResponseModel<?> getAllTasks() {
        return getAllTaskApi.execute(new CommonRequest());
    }

    @GetMapping("/search")
    public ResponseModel<?> searchTask(@RequestParam(required = false) String userId
            , @RequestParam(required = false) String taskType
            , @RequestParam(required = false) TaskStatus taskStatus
            , @RequestParam(required = false) String text
            , @RequestParam Integer page
            , @RequestParam Integer limit) {
        SearchTaskRequest wrapData = new SearchTaskRequest();
        wrapData.setUserId(userId);
        wrapData.setType(TaskType.fromValue(taskType));
        wrapData.setStatus(taskStatus);
        wrapData.setOtherText(text);
        wrapData.setPage(page);
        wrapData.setLimit(limit);
        return searchTaskApi.execute(wrapData);
    }

    @PostMapping("/assign")
    public ResponseModel<?> assignTask(@RequestBody AssignTaskRequest assignTaskRequest) {
        return assignTaskApi.execute(assignTaskRequest);
    }
}
