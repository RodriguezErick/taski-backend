package com.taski.projects.dto;

import com.taski.utils.Constants;
import jakarta.validation.constraints.NotBlank;

public class CreateProjectDTO {
    private Long userId;

    @NotBlank(message = "Name" + Constants.IS_MANDATORY_FIELD)
    private String name;

    @NotBlank(message = "Description" + Constants.IS_MANDATORY_FIELD)
    private String description;

    public CreateProjectDTO(){}

    public CreateProjectDTO(Long userId, String name, String description){
        this.name = name;
        this.description = description;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
