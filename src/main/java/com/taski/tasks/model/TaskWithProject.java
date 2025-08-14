package com.taski.tasks.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskWithProject {
    private Long id;
    private Long projectId;
    private String title;
    private String description;
    private boolean isCompleted;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private String projectName;
    private String projectDescription;
    private LocalDateTime projectCreatedAt;

    public TaskWithProject(Long id, Long projectId, String title, String description,
                                  boolean isCompleted, LocalDate dueDate, LocalDateTime createdAt, String projectName,
                           String projectDescription, LocalDateTime projectCreatedAt) {
        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectCreatedAt = projectCreatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public LocalDateTime getProjectCreatedAt() {
        return projectCreatedAt;
    }

    public void setProjectCreatedAt(LocalDateTime projectCreatedAt) {
        this.projectCreatedAt = projectCreatedAt;
    }

}
