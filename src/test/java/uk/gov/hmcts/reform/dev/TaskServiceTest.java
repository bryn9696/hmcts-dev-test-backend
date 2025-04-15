package uk.gov.hmcts.reform.dev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.reform.dev.Exceptions.TaskNotFoundException;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.repository.TaskRepository;
import uk.gov.hmcts.reform.dev.service.TaskService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskService service;

    private Task sampleTask;

    @BeforeEach
    void setUp() {
        sampleTask = new Task();
        sampleTask.setId(1L);
        sampleTask.setTitle("Sample Task");
        sampleTask.setStatus("Pending");
        sampleTask.setDueDate(LocalDateTime.now());
    }

    @Test
    void shouldCreateTask() {
        when(repository.save(sampleTask)).thenReturn(sampleTask);
        Task created = service.createTask(sampleTask);
        assertEquals("Sample Task", created.getTitle());
    }

    @Test
    void shouldReturnTaskById() {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleTask));
        Task task = service.getTaskById(1L);
        assertEquals(1L, task.getId());
    }

    @Test
    void shouldThrowIfTaskNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class, () -> service.getTaskById(99L));
    }

    @Test
    void shouldReturnAllTasks() {
        when(repository.findAll()).thenReturn(List.of(sampleTask));
        List<Task> tasks = service.getAllTasks();
        assertEquals(1, tasks.size());
    }

    @Test
    void shouldUpdateTask() {
        Task updated = new Task();
        updated.setTitle("Updated");
        updated.setDescription("New desc");
        updated.setStatus("In Progress");
        updated.setDueDate(LocalDateTime.now());

        when(repository.findById(1L)).thenReturn(Optional.of(sampleTask));
        when(repository.save(any(Task.class))).thenAnswer(i -> i.getArgument(0));

        Task result = service.updateTask(1L, updated);
        assertEquals("Updated", result.getTitle());
    }

    @Test
    void shouldDeleteTask() {
        when(repository.existsById(1L)).thenReturn(true);
        service.deleteTask(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void shouldThrowIfDeletingNonexistentTask() {
        when(repository.existsById(99L)).thenReturn(false);
        assertThrows(TaskNotFoundException.class, () -> service.deleteTask(99L));
    }
}
