package uk.gov.hmcts.reform.dev.Exceptions;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("Task with ID " + id + " not found.");
    }
}

