package uk.gov.hmcts.reform.dev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import uk.gov.hmcts.reform.dev.controllers.TaskController;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.service.TaskService;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService service;

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId(1L);
        task.setTitle("Title");
        task.setStatus("Pending");
        task.setDueDate(LocalDateTime.of(2025, 5, 1, 10, 0));
    }

    @Test
    void shouldCreateTask() throws Exception {
        when(service.createTask(any(Task.class))).thenReturn(task);

        String json = """
            {
                "title": "Title",
                "status": "Pending",
                "dueDate": "2025-05-01T10:00:00"
            }
        """;

        mockMvc.perform(MockMvcRequestBuilders
                            .post("/tasks")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value("Title"));
    }

    @Test
    void shouldUpdateTask() throws Exception {
        when(service.updateTask(Mockito.eq(1L), any(Task.class))).thenReturn(task);

        String updateJson = """
            {
                "title": "Updated",
                "status": "In Progress",
                "dueDate": "2025-05-01T10:00:00"
            }
        """;

        mockMvc.perform(put("/tasks/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(updateJson))
            .andExpect(status().isOk());
    }

    @Test
    void shouldReturnTaskById() throws Exception {
        when(service.getTaskById(1L)).thenReturn(task);

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Title"));
    }

    @Test
    void shouldReturnAllTasks() throws Exception {
        when(service.getAllTasks()).thenReturn(Collections.singletonList(task));

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].title").value("Title"));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/tasks/1"))
            .andExpect(status().isNoContent());
    }
}
