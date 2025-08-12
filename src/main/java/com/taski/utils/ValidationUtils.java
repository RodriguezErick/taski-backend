package com.taski.utils;

import com.taski.projects.service.ProjectService;
import com.taski.security.JwtService;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class ValidationUtils {
    public static Long getUserID(){
        return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

//    public static boolean userHasTask(int userId, int taskId, TaskService taskService) {
//        return taskService.getTasksByUser(userId)
//                .stream()
//                .anyMatch(task -> task.getId() == taskId);
//    }
}
