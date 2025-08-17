package com.taski.tags.service;

import com.taski.tags.dto.AddTagToTaskDTO;
import com.taski.tags.model.Tag;
import com.taski.tags.repository.TagsRepository;
import com.taski.tasks.service.TasksService;
import com.taski.utils.Constants;
import org.springframework.stereotype.Service;

@Service
public class TagsService {

    private final TagsRepository tagsRepository;

    private final TasksService tasksService;

    public TagsService(TagsRepository tagsRepository, TasksService tasksService){
        this.tagsRepository = tagsRepository;
        this.tasksService = tasksService;
    }

    public boolean addTagToTask(AddTagToTaskDTO tagDTO){
        if(!tasksService.userHasTask(tagDTO.getTaskId())){
            throw new IllegalStateException(Constants.NO_ACCESS_TO_TASK);
        }
        Tag tag = new Tag(tagDTO.getTagName(), tagDTO.getTaskId());

        return tagsRepository.addTagToTask(tag);
    }

    public void removeTagFromTask(Long taskId, String tagName){
        if (!tasksService.userHasTask(taskId)){
            throw new IllegalStateException(Constants.NO_ACCESS_TO_TASK);
        }
        tagsRepository.removeTagFromTask(taskId, tagName);
    }
}
