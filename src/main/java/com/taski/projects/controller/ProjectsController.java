package com.taski.projects.controller;

import com.taski.projects.dto.CreateProjectDTO;
import com.taski.projects.dto.UpdateProjectDTO;
import com.taski.projects.model.Project;
import com.taski.projects.service.ProjectService;
import com.taski.security.JwtService;
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
@RequestMapping("/projects")
public class ProjectsController {
    private final ProjectService projectService;


    public ProjectsController(ProjectService projectService){
        this.projectService = projectService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createProject(@Valid @RequestBody CreateProjectDTO projectDTO){
        try{
            projectDTO.setUserId(Utils.getUserID());
            projectService.createProject(projectDTO);
            Map<String, String> projectInfo = new HashMap<>();
            projectInfo.put("name", projectDTO.getName());

            ApiResponse<Map<String, String>> response = new ApiResponse<>(
                    "success",
                    "Project created successfully",
                    HttpStatus.CREATED.value(),
                    projectInfo
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
    public ResponseEntity<ApiResponse<?>> getProjectsByUser(){
        try{

            List<Project> result = projectService.getProjectsByUserId();

            ApiResponse<List<Project>> response = new ApiResponse<>(
                    "success",
                    "Got projects successfully.",
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
    public ResponseEntity<ApiResponse<?>> updateProjects(@Valid @RequestBody UpdateProjectDTO projectDTO){
        try{
            boolean updated = projectService.updateProject(projectDTO);

            if (!updated) {
                ApiResponse<Object> response = new ApiResponse<>("error", "No project found with id " + projectDTO.getId(), HttpStatus.NOT_FOUND.value());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            Map<String, String> projectInfo = new HashMap<>();
            projectInfo.put("id", projectDTO.getId().toString());
            projectInfo.put("name", projectDTO.getName());

            ApiResponse<Map<String, String>> response = new ApiResponse<>(
                    "success",
                    "Project updated successfully",
                    HttpStatus.OK.value(),
                    projectInfo
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
    public ResponseEntity<ApiResponse<?>> deleteProject(@PathVariable Long id){
        try{
            projectService.deleteProject(id);
            Map<String, String> projectInfo = new HashMap<>();
            projectInfo.put("id", id.toString());

            ApiResponse<Map<String, String>> response = new ApiResponse<>(
                    "success",
                    "Project Deleted successfully",
                    HttpStatus.OK.value(),
                    projectInfo
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
