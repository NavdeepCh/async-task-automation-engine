# Async Task Automation Engine

A high-throughput REST API backend for managing and executing non-blocking deployment workflows using Spring Boot, Spring Data JPA, and H2 database.

## Features

✅ **High-Throughput REST API** - Built with Spring Boot for managing deployment workflows  
✅ **Concurrent Task Processing** - Multi-threaded @Async workers to prevent thread blocking  
✅ **Spring Data JPA** - ORM-based data management with relational state tracking  
✅ **H2 In-Memory Database** - Fast, reliable caching of CI/CD logs and task states  
✅ **Automatic Retry Mechanism** - Configurable retry logic for failed tasks  
✅ **Deployment Log Tracking** - Complete audit trail of all workflow steps  

## Tech Stack

- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17
- **Database**: H2 (In-Memory)
- **ORM**: Spring Data JPA
- **Build Tool**: Maven
- **Concurrency**: @Async with ThreadPoolTaskExecutor

## Project Structure

```
src/main/java/com/automation/
├── AsyncTaskEngineApplication.java
├── config/
│   └── AsyncConfig.java
├── controller/
│   └── TaskController.java
├── entity/
│   ├── Task.java
│   ├── TaskStatus.java
│   └── DeploymentLog.java
├── repository/
│   ├── TaskRepository.java
│   └── DeploymentLogRepository.java
├── service/
│   ├── TaskService.java
│   ├── AsyncTaskProcessor.java
│   └── DeploymentLogService.java
└── dto/
    ├── TaskRequest.java
    └── TaskResponse.java
```

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+

### Installation

```bash
# Clone the repository
git clone https://github.com/NavdeepCh/async-task-automation-engine.git
cd async-task-automation-engine

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Create a Task
```bash
POST /api/tasks
Content-Type: application/json

{
  "name": "Deploy to Production",
  "description": "Deploy latest build to production",
  "workflowType": "DEPLOYMENT",
  "environment": "PROD"
}
```

### Get Task by ID
```bash
GET /api/tasks/{id}
```

### Get All Tasks
```bash
GET /api/tasks
```

### Get Tasks by Status
```bash
GET /api/tasks/status/{status}
# Status: PENDING, PROCESSING, COMPLETED, FAILED, RETRYING, CANCELLED
```

### Get Tasks by Workflow Type
```bash
GET /api/tasks/workflow/{workflowType}
```

### Get Deployment Logs for a Task
```bash
GET /api/tasks/{id}/logs
```

## Configuration

### Thread Pool Configuration
Edit `src/main/resources/application.properties`:

```properties
# Core threads
thread.pool.core=5
# Max threads
thread.pool.max=10
# Queue capacity
thread.pool.queue=100
```

### Database Console
Access H2 Console at: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:taskdb`
- Username: `sa`
- Password: (leave blank)

## How It Works

1. **Task Creation**: A task is created via REST API and stored in the database with PENDING status
2. **Async Processing**: `@Async` method triggers concurrent task processing without blocking the API thread
3. **Thread Pool**: Configured ThreadPoolTaskExecutor manages worker threads, preventing thread starvation
4. **Workflow Execution**: Task goes through deployment steps (build, test, deploy, health checks)
5. **Logging**: All steps are logged to `DeploymentLog` table for audit trail
6. **State Persistence**: Task status and results are stored in JPA entities
7. **Retry Logic**: Failed tasks are automatically retried up to 3 times

## Performance Benefits

- **Non-Blocking I/O**: API responds immediately while tasks process asynchronously
- **Concurrent Execution**: Multiple tasks can be processed in parallel
- **Bounded Resource Usage**: Thread pool prevents unbounded thread creation
- **Fast Caching**: H2 in-memory database provides sub-millisecond lookups
- **Scalable**: Can handle thousands of concurrent task submissions

## Example Response

```json
{
  "id": 1,
  "name": "Deploy to Production",
  "description": "Deploy latest build to production",
  "status": "PROCESSING",
  "workflowType": "DEPLOYMENT",
  "createdAt": "2026-01-15T10:30:00",
  "updatedAt": "2026-01-15T10:30:05",
  "retryCount": 0
}
```

## Contributing

Feel free to fork and submit pull requests for any improvements!

## License

MIT License - feel free to use this project

## Author

Navdeep Chhonkar
