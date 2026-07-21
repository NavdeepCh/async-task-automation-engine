package com.automation.repository;

import com.automation.entity.Task;
import com.automation.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus status);
    List<Task> findByWorkflowType(String workflowType);
    List<Task> findByStatusAndWorkflowType(TaskStatus status, String workflowType);
}
