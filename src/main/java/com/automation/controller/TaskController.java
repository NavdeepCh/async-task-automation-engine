package com.automation.controller;

import com.automation.service.TaskService;
import com.automation.service.DeploymentLogService;
import com.automation.dto.TaskRequest;
import com.automation.dto.TaskResponse;
import com.automation.entity.DeploymentLog;
import com.automation.entity.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private DeploymentLogService deploymentLogService;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest request) {
        TaskResponse response = taskService.createTask(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable Long id) {
        TaskResponse response = taskService.getTask(id);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        List<TaskResponse> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskResponse>> getTasksByStatus(@PathVariable TaskStatus status) {
        List<TaskResponse> tasks = taskService.getTasksByStatus(status);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/workflow/{workflowType}")
    public ResponseEntity<List<TaskResponse>> getTasksByWorkflowType(@PathVariable String workflowType) {
        List<TaskResponse> tasks = taskService.getTasksByWorkflowType(workflowType);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}/logs")
    public ResponseEntity<List<DeploymentLog>> getTaskLogs(@PathVariable Long id) {
        List<DeploymentLog> logs = deploymentLogService.getLogsForTask(id);
        return ResponseEntity.ok(logs);
    }
}
