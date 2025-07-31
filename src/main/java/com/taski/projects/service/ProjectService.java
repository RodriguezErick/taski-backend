package com.taski.projects.service;

import com.taski.account.model.User;
import com.taski.account.repository.UserRepository;
import com.taski.projects.model.Project;
import com.taski.projects.repository.ProjectsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectsRepository projectsRepository;
    private final UserRepository userRepository;

     public ProjectService(ProjectsRepository projectsRepository, UserRepository userRepository){
         this.projectsRepository = projectsRepository;
         this.userRepository = userRepository;
     }

     public List<Project> getProjectsByUserId(Long userId){
         Optional<User> userOPT = userRepository.getUserById(userId);
         if (userOPT.isEmpty()){
             throw new IllegalStateException("User not found, invalid or expired token.");
         }

         User user = userOPT.get();

         return projectsRepository.getProjectsByUserId(user.getId());

     }
}
