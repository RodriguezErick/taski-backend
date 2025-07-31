package com.taski.projects.controller;

import com.taski.projects.model.Project;
import com.taski.projects.service.ProjectService;
import com.taski.security.JwtService;
import com.taski.utils.ApiResponse;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/projects")
public class ProjectsController {
    private final ProjectService projectService;
    private final JwtService jwtService;


    public ProjectsController(ProjectService projectService, JwtService jwtService){
        this.projectService = projectService;
        this.jwtService = jwtService;
    }

    @GetMapping("/user-projects")
    public ResponseEntity<ApiResponse<?>> getProjectsByUser(@RequestHeader("Authorization") String authHeader){
        try{
            String token = authHeader.replace("Bearer ", "");
            Long userId = jwtService.extractUserId(token);

            List<Project> result = projectService.getProjectsByUserId(userId);

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
}
