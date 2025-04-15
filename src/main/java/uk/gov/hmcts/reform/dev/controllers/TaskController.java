package uk.gov.hmcts.reform.dev.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@Validated
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createTask(task));
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable Long id) {
        return service.getTaskById(id);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return service.getAllTasks();
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody Task updatedTask) {
        Task task = service.updateTask(taskId, updatedTask);
        if (task != null) {
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        service.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
