package com.taski.tags.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Long taskId;

    public Tag(){}

    public Tag (Long id, String name){
        this.id = id;
        this.name = name;
    }
    public Tag (String name, Long taskId){
        this.name = name;
        this.taskId = taskId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
