package com.tasks.services;

import com.tasks.entity.Task;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.PriorityQueue;

@Service
public class TaskPriorityQueueService {
    private final PriorityQueue<Task> taskPriorityQueue;

    public TaskPriorityQueueService() {
        taskPriorityQueue = new PriorityQueue<>(Comparator.comparing(Task::getDueDate));
    }

    public void addToQueue(Task task) {
        taskPriorityQueue.add(task);
    }

    public Task getHighestPriorityTask() {
        return taskPriorityQueue.peek();
    }

    public Task removeHighestPriorityTask() {
        return taskPriorityQueue.poll();
    }

    public boolean isEmpty() {
        return taskPriorityQueue.isEmpty();
    }
}
