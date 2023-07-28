package com.tasks.services;

import com.amazonaws.services.datapipeline.model.TaskNotFoundException;
import com.tasks.entity.Task;
import com.tasks.entity.TaskStatistics;
import com.tasks.entity.User;
import com.tasks.exception.RestExceptionHandler;
import com.tasks.repository.TaskRepository;
import com.tasks.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskPriorityQueueService taskPriorityQueueService;

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long taskId, Task updatedTask) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setDueDate(updatedTask.getDueDate());
        task.setStatus(updatedTask.getStatus());
        if ("Completed".equalsIgnoreCase(updatedTask.getStatus())) {
            task.setCompletedDate(LocalDate.now());
        }

        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task assignTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestExceptionHandler.UserNotFoundException("User not found with id: " + userId));

        task.setAssignedUser(user);
        taskRepository.save(task);
        return task;
    }

    public List<Task> getTasksAssignedToUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RestExceptionHandler.UserNotFoundException("User not found with id: " + userId));

        return taskRepository.findByAssignedUser(user);
    }

    public int getTaskProgress(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));


        return 100;
    }

    public List<Task> getOverdueTasks() {
        LocalDate currentDate = LocalDate.now();
        return taskRepository.findByDueDateBefore(currentDate);
    }

    public List<Task> getTasksByStatus(String status) {
        return taskRepository.findAllByStatus(status);
    }

    public List<Task> getCompletedTasksByDateRange(LocalDate startDate, LocalDate endDate) {
        return taskRepository.findByCompletedDateBetween(startDate, endDate);
    }

    public TaskStatistics getTaskStatistics() {
        long totalTasks = taskRepository.count();
        long completedTasks = taskRepository.countByStatus("Completed");
        double percentageCompleted = (completedTasks / (double) totalTasks) * 100.0;

        return new TaskStatistics(totalTasks, completedTasks, percentageCompleted);
    }
}
