package com.assistico.planner.dto.mappers;

import com.assistico.planner.dto.api.TaskResponse;
import com.assistico.planner.dto.request.TaskRequest;
import com.assistico.planner.model.Task;

public class TaskMapper {

    public static Task taskRequestToTask(TaskRequest taskRequest) {
        return Task.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .points(taskRequest.getPoints())
                .build();
    }

    public static TaskResponse taskToTaskResponse(Task task) {
        return TaskResponse.builder()
                .title(task.getTitle())
                .id(task.getId())
                .Description(task.getDescription())
                .points(task.getPoints())
                .build();
    }

}
