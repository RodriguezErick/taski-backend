package com.taski.tags.controller;

import com.taski.projects.dto.CreateProjectDTO;
import com.taski.tags.dto.AddTagToTaskDTO;
import com.taski.tags.service.TagsService;
import com.taski.utils.ApiResponse;
import com.taski.utils.Utils;
import jakarta.validation.Valid;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tags")
public class TagsController {
    private final TagsService tagsService;

    public TagsController(TagsService tagsService){
        this.tagsService = tagsService;
    }

    @PostMapping("/add-tag-to-task")
    public ResponseEntity<ApiResponse<?>> createProject(@Valid @RequestBody AddTagToTaskDTO tagDTO){
        try{
            boolean tagAdded = tagsService.addTagToTask(tagDTO);
            Map<String, String> tagInfo = new HashMap<>();
            tagInfo.put("name", tagDTO.getTagName());

            if (tagAdded) {
                ApiResponse<Map<String, String>> response = new ApiResponse<>(
                        "success",
                        "Tag added successfully",
                        HttpStatus.CREATED.value(),
                        tagInfo
                );
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                ApiResponse<Map<String, String>> response = new ApiResponse<>(
                        "Rejected",
                        "Tag already exists",
                        HttpStatus.OK.value(),
                        tagInfo
                );
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (DataAccessException e) {
            String rootMessage = e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage();

            ApiResponse<Object> response = new ApiResponse<>("error", "Database error: " + rootMessage, HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        } catch (Exception e) {
            ApiResponse<Object> response = new ApiResponse<>("error", "Unexpected error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{taskId}/{tagName}")
    public ResponseEntity<ApiResponse<?>> deleteProject(@PathVariable Long taskId, @PathVariable String tagName){
        try{
            tagsService.removeTagFromTask(taskId, tagName);

            ApiResponse<Map<String, String>> response = new ApiResponse<>(
                    "success",
                    "Tag <<" + tagName + ">> was removed from task <<" + taskId + ">>.",
                    HttpStatus.OK.value()
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (DataAccessException e) {
            String rootMessage = e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage();

            ApiResponse<Object> response = new ApiResponse<>("error", "Database error: " + rootMessage, HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        } catch (Exception e) {
            ApiResponse<Object> response = new ApiResponse<>("error", "Unexpected error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
