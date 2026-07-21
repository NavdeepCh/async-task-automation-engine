package com.automation.service;

import com.automation.entity.Task;
import com.automation.repository.TaskRepository;
import com.automation.repository.DeploymentLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncTaskProcessor {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private DeploymentLogService deploymentLogService;

    @Async("taskExecutor")
    public CompletableFuture<String> processTask(Long taskId) {
        try {
            Optional<Task> task = taskRepository.findById(taskId);
            if (task.isPresent()) {
                Task t = task.get();
                deploymentLogService.logMessage(taskId, "Task processing started", "INFO", t.getEnvironment());

                // Simulate deployment workflow
                simulateDeployment(t);

                deploymentLogService.logMessage(taskId, "Task processing completed successfully", "INFO", t.getEnvironment());
                return CompletableFuture.completedFuture("Task " + taskId + " completed");
            }
        } catch (Exception e) {
            deploymentLogService.logMessage(taskId, "Error: " + e.getMessage(), "ERROR", null);
            throw new RuntimeException("Task processing failed", e);
        }
        return CompletableFuture.failedFuture(new Exception("Task not found"));
    }

    private void simulateDeployment(Task task) throws InterruptedException {
        // Simulate CI/CD workflow steps
        deploymentLogService.logMessage(task.getId(), "Fetching deployment artifacts...", "INFO", task.getEnvironment());
        Thread.sleep(1000);

        deploymentLogService.logMessage(task.getId(), "Building application...", "INFO", task.getEnvironment());
        Thread.sleep(2000);

        deploymentLogService.logMessage(task.getId(), "Running tests...", "INFO", task.getEnvironment());
        Thread.sleep(1500);

        deploymentLogService.logMessage(task.getId(), "Deploying to " + task.getEnvironment() + "...", "INFO", task.getEnvironment());
        Thread.sleep(1000);

        deploymentLogService.logMessage(task.getId(), "Health checks passed", "INFO", task.getEnvironment());
    }
}
