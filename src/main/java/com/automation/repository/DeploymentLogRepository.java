package com.automation.repository;

import com.automation.entity.DeploymentLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DeploymentLogRepository extends JpaRepository<DeploymentLog, Long> {
    List<DeploymentLog> findByTaskId(Long taskId);
    List<DeploymentLog> findByTaskIdOrderByTimestampDesc(Long taskId);
}
