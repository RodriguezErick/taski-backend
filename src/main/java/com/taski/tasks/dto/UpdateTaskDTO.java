package com.taski.tasks.dto;

import com.taski.utils.Constants;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class UpdateTaskDTO {
    @NotBlank(message = "Task ID" + Constants.IS_MANDATORY_FIELD)
    private Long id;

    @NotBlank(message = "Project ID" + Constants.IS_MANDATORY_FIELD)
    private Long projectId;

    @NotBlank(message = "Title" + Constants.IS_MANDATORY_FIELD)
    private String title;

    @NotBlank(message = "Is Completed" + Constants.IS_MANDATORY_FIELD)
    private boolean isCompleted;

    @NotBlank(message = "Description" + Constants.IS_MANDATORY_FIELD)
    private String description;

    @NotBlank(message = "Due Date" + Constants.IS_MANDATORY_FIELD)
    private LocalDate dueDate;

    public UpdateTaskDTO(){}

    public UpdateTaskDTO(Long id, Long projectId, String title, boolean isCompleted, String description, LocalDate dueDate){
        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.isCompleted = isCompleted;
        this.description = description;
        this.dueDate = dueDate;
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

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
