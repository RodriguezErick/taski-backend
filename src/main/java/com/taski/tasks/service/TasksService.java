package com.taski.tasks.service;

import com.taski.account.model.User;
import com.taski.account.repository.UserRepository;
import com.taski.projects.service.ProjectService;
import com.taski.tasks.dto.CreateTaskDTO;
import com.taski.tasks.dto.UpdateTaskDTO;
import com.taski.tasks.model.Task;
import com.taski.tasks.model.TaskDetails;
import com.taski.tasks.model.TaskWithProject;
import com.taski.tasks.repository.TasksRepository;
import com.taski.utils.Constants;
import com.taski.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TasksService {
    private final UserRepository userRepository;
    private final TasksRepository tasksRepository;
    private final ProjectService projectService;

    public TasksService(UserRepository userRepository, TasksRepository tasksRepository, ProjectService projectService){
        this.userRepository = userRepository;
        this.tasksRepository = tasksRepository;
        this.projectService = projectService;
    }

    public void createTask(CreateTaskDTO taskDTO){
        if (!projectService.userHasProject(taskDTO.getProjectId())){
            throw new IllegalStateException(Constants.NO_ACCESS_TO_PROJECT);
        }
        Task task = new Task(taskDTO.getProjectId(), taskDTO.getTitle(), taskDTO.getDescription(), taskDTO.getDueDate());
        tasksRepository.createTask(task);
    }

    public boolean updateTask(UpdateTaskDTO taskDTO){
        if (!projectService.userHasProject(taskDTO.getProjectId())){
            throw new IllegalStateException(Constants.NO_ACCESS_TO_PROJECT);
        }
        if(!userHasTask(taskDTO.getId())){
            throw new IllegalStateException(Constants.NO_ACCESS_TO_TASK);
        }

        Task task = new Task(taskDTO.getId(), taskDTO.getProjectId(), taskDTO.getTitle(), taskDTO.getDescription(),
                taskDTO.isCompleted(), taskDTO.getDueDate());

        int rowsAffected = tasksRepository.updateTask(task);

        return rowsAffected > 0;
    }

    public void deleteTask(Long id){
        if (!userHasTask(id)){
            throw new IllegalStateException(Constants.NO_ACCESS_TO_TASK);
        }
        tasksRepository.deleteTask(id);
    }

    public List<TaskWithProject> getTasksByUserId(){
        Long userId = Utils.getUserID();
        Optional<User> userOPT = userRepository.getUserById(userId);
        if (userOPT.isEmpty()){
            throw new IllegalStateException("User not found, invalid or expired token.");
        }

        User user = userOPT.get();

        return tasksRepository.getTasksByUserId(user.getId());
    }

    public List<TaskDetails> getTasksByProjectId(Long projectId){
        Long userId = Utils.getUserID();
        Optional<User> userOPT = userRepository.getUserById(userId);
        if (userOPT.isEmpty()){
            throw new IllegalStateException("User not found, invalid or expired token.");
        }

        if(!projectService.userHasProject(projectId)){
            throw new IllegalStateException(Constants.NO_ACCESS_TO_PROJECT);
        }

        return tasksRepository.getTaskByProject(projectId);
    }

    public boolean userHasTask(Long taskId) {
        return getTasksByUserId()
                .stream()
                .anyMatch(task -> Objects.equals(task.getId(), taskId));
    }
}
