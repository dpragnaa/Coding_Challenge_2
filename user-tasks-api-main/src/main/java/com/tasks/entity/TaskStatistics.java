package com.tasks.entity;

import lombok.Data;

@Data
public class TaskStatistics {
    private long totalTasks;
    private long completedTasks;
    private double percentageCompleted;

    public TaskStatistics(long totalTasks, long completedTasks, double percentageCompleted) {
        this.totalTasks=totalTasks;
        this.completedTasks=completedTasks;
       this.percentageCompleted=percentageCompleted;
    }
}
