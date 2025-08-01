package com.taski.projects.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateProjectDTO {
    @NotBlank(message = "Project id is required.")
    private Long id;

    @NotBlank(message = "Project name is required.")
    private String name;

    @NotBlank(message = "Project description is required.")
    private String description;

    public UpdateProjectDTO(){}

    public UpdateProjectDTO(Long id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
