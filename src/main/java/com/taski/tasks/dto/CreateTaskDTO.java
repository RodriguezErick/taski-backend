package com.taski.tasks.dto;

import com.taski.projects.dto.CreateProjectDTO;
import com.taski.utils.Constants;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class CreateTaskDTO {
    @NotBlank(message = "Project Id" + Constants.IS_MANDATORY_FIELD)
    private Long projectId;

    @NotBlank(message = "Title" + Constants.IS_MANDATORY_FIELD)
    private String title;

    @NotBlank(message = "Description" + Constants.IS_MANDATORY_FIELD)
    private String description;

    @NotBlank(message = "Due Date" + Constants.IS_MANDATORY_FIELD)
    private LocalDate dueDate;

    public CreateTaskDTO(){}

    public CreateTaskDTO(Long projectId, String title, String description, LocalDate dueDate){
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
