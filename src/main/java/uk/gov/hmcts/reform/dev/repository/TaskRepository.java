package uk.gov.hmcts.reform.dev.repository;

import uk.gov.hmcts.reform.dev.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {}
