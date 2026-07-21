package com.automation.service;

import com.automation.entity.Task;
import com.automation.entity.TaskStatus;
import com.automation.repository.TaskRepository;
import com.automation.dto.TaskRequest;
import com.automation.dto.TaskResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AsyncTaskProcessor asyncTaskProcessor;

    public TaskResponse createTask(TaskRequest request) {
        Task task = new Task();
        task.setName(request.getName());
        task.setDescription(request.getDescription());
        task.setStatus(TaskStatus.PENDING);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        task.setWorkflowType(request.getWorkflowType());
        task.setRetryCount(0);

        Task savedTask = taskRepository.save(task);
        
        // Trigger async processing
        asyncTaskProcessor.processTask(savedTask.getId());

        return new TaskResponse(savedTask);
    }

    public TaskResponse getTask(Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        return task.map(TaskResponse::new).orElse(null);
    }

    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
    }

    public List<TaskResponse> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status)
                .stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
    }

    public List<TaskResponse> getTasksByWorkflowType(String workflowType) {
        return taskRepository.findByWorkflowType(workflowType)
                .stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
    }

    public void updateTaskStatus(Long taskId, TaskStatus status) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isPresent()) {
            Task t = task.get();
            t.setStatus(status);
            t.setUpdatedAt(LocalDateTime.now());
            taskRepository.save(t);
        }
    }

    public void completeTask(Long taskId, String result) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isPresent()) {
            Task t = task.get();
            t.setStatus(TaskStatus.COMPLETED);
            t.setCompletedAt(LocalDateTime.now());
            t.setResult(result);
            t.setUpdatedAt(LocalDateTime.now());
            taskRepository.save(t);
        }
    }

    public void failTask(Long taskId, String errorMessage) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isPresent()) {
            Task t = task.get();
            if (t.getRetryCount() < 3) {
                t.setStatus(TaskStatus.RETRYING);
                t.setRetryCount(t.getRetryCount() + 1);
                asyncTaskProcessor.processTask(taskId);
            } else {
                t.setStatus(TaskStatus.FAILED);
                t.setResult("Failed after " + t.getRetryCount() + " retries: " + errorMessage);
            }
            t.setUpdatedAt(LocalDateTime.now());
            taskRepository.save(t);
        }
    }
}
