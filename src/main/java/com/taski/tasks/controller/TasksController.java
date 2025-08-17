package com.taski.tasks.controller;

import com.taski.projects.dto.CreateProjectDTO;
import com.taski.projects.dto.UpdateProjectDTO;
import com.taski.projects.model.Project;
import com.taski.tasks.dto.CreateTaskDTO;
import com.taski.tasks.dto.UpdateTaskDTO;
import com.taski.tasks.model.TaskDetails;
import com.taski.tasks.model.TaskWithProject;
import com.taski.tasks.service.TasksService;
import com.taski.utils.ApiResponse;
import com.taski.utils.Utils;
import jakarta.validation.Valid;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    private final TasksService tasksService;

    public TasksController(TasksService tasksService){
        this.tasksService = tasksService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createTask(@Valid @RequestBody CreateTaskDTO taskDTO){
        try{
            tasksService.createTask(taskDTO);
            Map<String, String> taskInfo = new HashMap<>();
            taskInfo.put("title", taskDTO.getTitle());

            ApiResponse<Map<String, String>> response = new ApiResponse<>(
                    "success",
                    "Task created successfully",
                    HttpStatus.CREATED.value(),
                    taskInfo
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (DataAccessException e) {
            String rootMessage = e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage();

            ApiResponse<Object> response = new ApiResponse<>("error", "Database error: " + rootMessage, HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        } catch (Exception e) {
            ApiResponse<Object> response = new ApiResponse<>("error", "Unexpected error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/by-user")
    public ResponseEntity<ApiResponse<?>> getTasksByUser(){
        try{

            List<TaskWithProject> result = tasksService.getTasksByUserId();

            ApiResponse<List<TaskWithProject>> response = new ApiResponse<>(
                    "success",
                    "Got Tasks successfully.",
                    HttpStatus.OK.value(),
                    result
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (DataAccessException e){

            String rootMessage = e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage();

            ApiResponse<Object> response = new ApiResponse<>("error", "Database error: " + rootMessage, HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            ApiResponse<Object> response = new ApiResponse<>("error", "Unexpected error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/by-project/{projectId}")
    public ResponseEntity<ApiResponse<?>> getTasksByProject(@PathVariable Long projectId){
        try{

            List<TaskDetails> result = tasksService.getTasksByProjectId(projectId);

            ApiResponse<List<TaskDetails>> response = new ApiResponse<>(
                    "success",
                    "Got Tasks successfully.",
                    HttpStatus.OK.value(),
                    result
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (DataAccessException e){

            String rootMessage = e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage();

            ApiResponse<Object> response = new ApiResponse<>("error", "Database error: " + rootMessage, HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            ApiResponse<Object> response = new ApiResponse<>("error", "Unexpected error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<ApiResponse<?>> updateTask(@Valid @RequestBody UpdateTaskDTO taskDTO){
        try{
            boolean updated = tasksService.updateTask(taskDTO);

            if (!updated) {
                ApiResponse<Object> response = new ApiResponse<>("error", "No task found with id " + taskDTO.getId(), HttpStatus.NOT_FOUND.value());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            Map<String, String> taskInfo = new HashMap<>();
            taskInfo.put("id", taskDTO.getId().toString());
            taskInfo.put("name", taskDTO.getTitle());

            ApiResponse<Map<String, String>> response = new ApiResponse<>(
                    "success",
                    "Project updated successfully",
                    HttpStatus.OK.value(),
                    taskInfo
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

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteTask(@PathVariable Long id){
        try{
            tasksService.deleteTask(id);
            Map<String, String> taskInfo = new HashMap<>();
            taskInfo.put("id", id.toString());

            ApiResponse<Map<String, String>> response = new ApiResponse<>(
                    "success",
                    "Task Deleted successfully",
                    HttpStatus.OK.value(),
                    taskInfo
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
