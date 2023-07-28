package com.tasks.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDate dueDate;
    private String status;
    private LocalDate completedDate;

    @ManyToOne
    @JoinColumn(name = "user_id") // Assuming the foreign key column name in the Task table is 'user_id'
    private User assignedUser;



    public void setAssignedUser(User user) {
    }
}
