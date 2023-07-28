package com.tasks.repository;

import com.tasks.entity.Task;
import com.tasks.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByStatus(String status);
    List<Task> findByDueDateBefore(LocalDate date);
    List<Task> findByCompletedDateBetween(LocalDate startDate, LocalDate endDate);

    long countByStatus(String completed);

    List<Task> findByAssignedUser(User user);
}