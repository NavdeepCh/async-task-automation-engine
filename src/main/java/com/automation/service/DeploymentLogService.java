package com.automation.service;

import com.automation.entity.DeploymentLog;
import com.automation.repository.DeploymentLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeploymentLogService {
    @Autowired
    private DeploymentLogRepository deploymentLogRepository;

    public void logMessage(Long taskId, String message, String logLevel, String environment) {
        DeploymentLog log = new DeploymentLog();
        log.setTaskId(taskId);
        log.setLogContent(message);
        log.setLogLevel(logLevel);
        log.setTimestamp(LocalDateTime.now());
        log.setEnvironment(environment);
        deploymentLogRepository.save(log);
    }

    public List<DeploymentLog> getLogsForTask(Long taskId) {
        return deploymentLogRepository.findByTaskIdOrderByTimestampDesc(taskId);
    }
}
