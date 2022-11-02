package com.assistico.planner.controller;

import com.assistico.planner.dto.api.TaskResponse;
import com.assistico.planner.dto.request.TaskRequest;
import com.assistico.planner.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping()
    public ResponseEntity<TaskResponse> addTask(@RequestBody @Valid TaskRequest taskRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(this.taskService.addTask(taskRequest));
    }

    @GetMapping()
    public List<TaskResponse> getAllTasks() {
        return this.taskService.getAllTasks();
    }

    @PutMapping()
    public ResponseEntity<TaskResponse> editTask(@RequestBody @Valid TaskRequest taskRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(this.taskService.editTask(taskRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable(value = "id") String id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.taskService.deleteTaskById(Long.parseLong(id)));
    }

}