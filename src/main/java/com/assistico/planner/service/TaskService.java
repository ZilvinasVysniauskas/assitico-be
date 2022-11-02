package com.assistico.planner.service;

import com.assistico.planner.dto.api.TaskResponse;
import com.assistico.planner.dto.mappers.TaskMapper;
import com.assistico.planner.dto.request.TaskRequest;
import com.assistico.planner.model.Task;
import com.assistico.planner.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskResponse addTask(TaskRequest taskRequest) {
        return this.saveAndReturnResponseWithCreatedTask(taskRequest);
    }

    public TaskResponse editTask(TaskRequest taskRequest) {
        return this.saveAndReturnResponseWithCreatedTask(taskRequest);
    }

    public String deleteTaskById(Long id) {
        this.taskRepository.deleteById(id);
        return String.format("Tasks with Id %d was deleted", id);
    }

    public List<TaskResponse> getAllTasks() {
        return this.taskRepository.findAll().stream().map(TaskMapper::taskToTaskResponse).collect(Collectors.toList());
    }

    private TaskResponse saveAndReturnResponseWithCreatedTask(TaskRequest taskRequest) {
        Task taskToSave = TaskMapper.taskRequestToTask(taskRequest);
        Task savedTask = this.taskRepository.save(taskToSave);
        return TaskMapper.taskToTaskResponse(savedTask);
    }


}
