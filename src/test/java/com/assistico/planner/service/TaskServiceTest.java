package com.assistico.planner.service;



import com.assistico.planner.dto.api.TaskResponse;
import com.assistico.planner.dto.mappers.TaskMapper;
import com.assistico.planner.dto.request.TaskRequest;
import com.assistico.planner.model.Task;
import com.assistico.planner.repository.TaskRepository;
import com.assistico.planner.utils.Points;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;



import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {TaskService.class, TaskRepository.class})
@AutoConfigureMockMvc
class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskRepository taskRepository;

    private List<Task> allTasksInDb;
    private TaskResponse taskOneResponse;
    private TaskResponse taskTwoResponse;


    @BeforeEach
    public void init() {
        Task task1 = Task.builder()
                .id(1L)
                .title("title1")
                .description("description1")
                .points(Points.FIVE)
                .build();
        Task task2 = Task.builder()
                .id(2L)
                .title("title2")
                .description("description2")
                .points(Points.ONE)
                .build();
        this.taskOneResponse = TaskMapper.taskToTaskResponse(task1);
        this.taskTwoResponse = TaskMapper.taskToTaskResponse(task2);
        this.allTasksInDb = List.of(task1, task2);
    }

    @Test
    @DisplayName("Test findAll")
    void findAllTasks() {
        System.out.println(this.taskOneResponse);
        doReturn(this.allTasksInDb).when(this.taskRepository).findAll();
        List<TaskResponse> actualRerun = this.taskService.getAllTasks();
        List<TaskResponse> expectedReturn = List.of(this.taskOneResponse, this.taskTwoResponse);
        assertEquals(expectedReturn, actualRerun);
    }

    @Test
    @DisplayName("Test deleteTask")
    void deleteTask() {
        String expectedReturn = "Tasks with Id 1 was deleted";
        String actualReturn = this.taskService.deleteTaskById(1L);
        assertEquals(expectedReturn, actualReturn);
    }

    @Test
    @DisplayName("Test addTask")
    void addTask() {
        //add task with unique title
        TaskRequest taskToAdd =  TaskRequest.builder()
                .title("title3")
                .points(Points.FIVE)
                .description("description")
                .build();
        TaskResponse expectedReturn = TaskResponse.builder()
                .id(5L)
                .points(Points.FIVE)
                .Description("description")
                .build();
        Task savedTask = Task.builder()
                .id(5L)
                .points(Points.FIVE)
                .description("description")
                .build();
        doReturn(savedTask).when(this.taskRepository).save(any(Task.class));
        TaskResponse actualReturn = this.taskService.addTask(taskToAdd);
        assertEquals(expectedReturn, actualReturn);
    }

    @Test
    @DisplayName("Test addTask")
    void editTask() {
        //add task with unique title
        TaskRequest taskToEdit =  TaskRequest.builder()
                .id(5L)
                .title("title3")
                .points(Points.FIVE)
                .description("description")
                .build();
        TaskResponse expectedReturn = TaskResponse.builder()
                .id(5L)
                .points(Points.FIVE)
                .Description("description")
                .build();
        Task savedTask = Task.builder()
                .id(5L)
                .points(Points.FIVE)
                .description("description")
                .build();
        doReturn(savedTask).when(this.taskRepository).save(any(Task.class));
        TaskResponse actualReturn = this.taskService.editTask(taskToEdit);
        assertEquals(expectedReturn, actualReturn);
    }
}