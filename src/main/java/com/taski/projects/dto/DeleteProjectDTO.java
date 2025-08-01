package com.taski.projects.dto;

public class DeleteProjectDTO {
    private Long id;

    public DeleteProjectDTO(){}

    public DeleteProjectDTO(Long id){
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
