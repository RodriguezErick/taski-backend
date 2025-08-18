package com.taski.tags.dto;

import com.taski.utils.Constants;
import jakarta.validation.constraints.NotBlank;

public class AddTagToTaskDTO {
    @NotBlank(message = "Task ID" + Constants.IS_MANDATORY_FIELD)
    private Long taskId;

    @NotBlank(message = "Tag Name" + Constants.IS_MANDATORY_FIELD)
    private String tagName;

    public AddTagToTaskDTO(){}

    public AddTagToTaskDTO(Long taskId, String tagName){
        this.taskId = taskId;
        this.tagName = tagName;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
