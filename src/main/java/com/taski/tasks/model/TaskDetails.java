package com.taski.tasks.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TaskDetails {
    private Long taskId;
    private Long projectId;
    private String title;
    private String description;
    private boolean isCompleted;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private List<String> tags;

    public TaskDetails(Long taskId, Long projectId, String title, String description, boolean isCompleted, LocalDate dueDate,
                       LocalDateTime createdAt, List<String> tags){
        this.taskId = taskId;
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.tags = tags;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
