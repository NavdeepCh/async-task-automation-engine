package com.automation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "deployment_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeploymentLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long taskId;

    @Column(columnDefinition = "TEXT")
    private String logContent;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private String logLevel; // INFO, WARNING, ERROR, DEBUG

    private String environment;
}
