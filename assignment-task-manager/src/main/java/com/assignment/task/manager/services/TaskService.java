package com.assignment.task.manager.services;

import com.assignment.core.common.CommonService;
import com.assignment.core.model.PagingResponse;
import com.assignment.task.manager.entities.TaskEntity;
import com.assignment.task.manager.model.request.task.SearchTaskRequest;
import com.assignment.task.manager.model.response.task.SearchTaskResponse;
import com.assignment.task.manager.repositories.TaskRepository;
import com.assignment.task.manager.repositories.custom.TaskRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService extends CommonService<TaskEntity,String, TaskRepository> {
    private final TaskRepositoryCustom taskRepositoryCustom;
    protected TaskService(TaskRepository repo, TaskRepositoryCustom taskRepositoryCustom) {
        super(repo);
        this.taskRepositoryCustom = taskRepositoryCustom;
    }
    public PagingResponse<SearchTaskResponse> searchTasks(SearchTaskRequest searchTaskRequest, Pageable pageable) {
        return taskRepositoryCustom.search(searchTaskRequest,pageable);
    }

    public List<TaskEntity> getAll(){
        return repo.findAll();
    }
    @Override
    public TaskEntity delete(TaskEntity entity) {
        entity.setIsDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        return save(entity);
    }

    @Override
    public TaskEntity deleteById(String s) {
        TaskEntity e = getOrElseThrow(s);
        return delete(e);
    }

    @Override
    protected String notFoundMessage() {
        return "Task not found";
    }
}
