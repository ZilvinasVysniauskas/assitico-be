package com.assistico.planner.controller;

import com.assistico.planner.dto.api.TaskResponse;
import com.assistico.planner.dto.request.TaskRequest;
import com.assistico.planner.service.TaskService;
import com.assistico.planner.utils.Points;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {
    private final String URI = "/api/v1/tasks";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;


    Logger logger = LoggerFactory.getLogger(TaskControllerTest.class);


    @Test
    @DisplayName("GET /api/v1/tasks/add-task")
    void addTask() throws Exception {
        TaskRequest mockTask = TaskRequest.builder()
                .title("test-title")
                .points(Points.FIVE)
                .description("description")
                .build();

        TaskResponse mockedServiceResponse = TaskResponse.builder()
                .id(1L)
                .Description("description")
                .points(Points.FIVE)
                .title("test-title")
                .build();

        String inputInJson = objectMapper.writeValueAsString(mockTask);

        doReturn(mockedServiceResponse).when(this.taskService).addTask(any(TaskRequest.class));

        String expectedReturnJson = objectMapper.writeValueAsString(mockedServiceResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(URI)
                .accept(MediaType.APPLICATION_JSON).content(inputInJson)
                .contentType(MediaType.APPLICATION_JSON);

        String actualReturnJson = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        assertEquals(actualReturnJson, expectedReturnJson);

        //testing invalid input
        TaskRequest mockTaskWithInvalidTitle = TaskRequest.builder()
                .title("w")
                .points(Points.FIVE)
                .description("description")
                .build();

        String invalidInputJson = objectMapper.writeValueAsString(mockTaskWithInvalidTitle);
        RequestBuilder failingRequest = MockMvcRequestBuilders
                .post(URI)
                .accept(MediaType.APPLICATION_JSON).content(invalidInputJson)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(failingRequest).andExpect(status().isBadRequest());
        logger.trace("A TRACE Message");
        logger.debug("A DEBUG Message");
        logger.info("An INFO Message");
        logger.warn("A WARN Message");
        logger.error("An ERROR Message");
    }

}