package com.automation.dto;

import com.automation.entity.Task;
import com.automation.entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {
    private Long id;
    private String name;
    private String description;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private String result;
    private Integer retryCount;
    private String workflowType;

    public TaskResponse(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.description = task.getDescription();
        this.status = task.getStatus();
        this.createdAt = task.getCreatedAt();
        this.updatedAt = task.getUpdatedAt();
        this.startedAt = task.getStartedAt();
        this.completedAt = task.getCompletedAt();
        this.result = task.getResult();
        this.retryCount = task.getRetryCount();
        this.workflowType = task.getWorkflowType();
    }
}
