package uk.gov.hmcts.reform.dev.service;

import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.dev.Exceptions.TaskNotFoundException;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.repository.TaskRepository;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public Task createTask(Task task) {
        return repository.save(task);
    }

    public Task getTaskById(Long id) {
        return repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task updateTask(Long id, Task updatedTask) {
        Task task = getTaskById(id);

        task.setTitle(updatedTask.getTitle());
        task.setStatus(updatedTask.getStatus());
        task.setDescription(updatedTask.getDescription());
        task.setDueDate(updatedTask.getDueDate());
        System.out.println(task);
        return repository.save(task);
    }

    public void deleteTask(Long id) {
        if (!repository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
