package com.taski.projects.service;

import com.taski.account.model.User;
import com.taski.account.repository.UserRepository;
import com.taski.projects.dto.CreateProjectDTO;
import com.taski.projects.dto.DeleteProjectDTO;
import com.taski.projects.dto.UpdateProjectDTO;
import com.taski.projects.model.Project;
import com.taski.projects.repository.ProjectsRepository;
import com.taski.utils.ValidationUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectsRepository projectsRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectsRepository projectsRepository, UserRepository userRepository){
         this.projectsRepository = projectsRepository;
         this.userRepository = userRepository;
     }

    public void createProject(CreateProjectDTO projectDTO){
         projectDTO.setUserId(ValidationUtils.getUserID());
         Project project = new Project(projectDTO.getUserId(), projectDTO.getName(), projectDTO.getDescription());
         projectsRepository.createProject(project);
    }

    public boolean updateProject(UpdateProjectDTO projectDTO){
        if(!userHasProject(projectDTO.getId())){
            throw new IllegalStateException("Current user has no access to this project.");
        }
        Project project = new Project(projectDTO.getName(),projectDTO.getId(), projectDTO.getDescription());
        int rowsAffected = projectsRepository.updateProject(projectDTO);
        return rowsAffected > 0;
    }

    public void deleteProject(Long id){
        if(!userHasProject(id)){
            throw new IllegalStateException("Current user has no access to this project.");
        }
         projectsRepository.deleteProject(id);
    }

    public List<Project> getProjectsByUserId(Long userId){
     Optional<User> userOPT = userRepository.getUserById(userId);
     if (userOPT.isEmpty()){
         throw new IllegalStateException("User not found, invalid or expired token.");
     }

     User user = userOPT.get();

     return projectsRepository.getProjectsByUserId(user.getId());
    }

    private boolean userHasProject(Long projectId) {
        Long userId = ValidationUtils.getUserID();
        return getProjectsByUserId(userId)
                .stream()
                .anyMatch(project -> Objects.equals(project.getId(), projectId));
    }

}
