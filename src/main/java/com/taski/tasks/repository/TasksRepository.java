package com.taski.tasks.repository;

import com.taski.projects.model.Project;
import com.taski.tasks.model.Task;
import com.taski.tasks.model.TaskDetails;
import com.taski.tasks.model.TaskWithProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class TasksRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void createTask(Task task){
        jdbcTemplate.update("CALL sp_create_task(?,?,?,?)", task.getProjectId(), task.getTitle(),
                task.getDescription(), task.getDueDate()
        );
    }

    public int updateTask(Task task){
        return jdbcTemplate.update("CALL sp_update_task(?,?,?,?,?,?)",
                Math.toIntExact(task.getId()), task.getTitle(), task.isCompleted(), task.getDescription(), task.getDueDate(),
                task.getProjectId()
        );
    }

    public void deleteTask(Long taskId){
        jdbcTemplate.update("CALL sp_delete_task(?)", taskId);
    }

    public List<TaskWithProject> getTasksByUserId(Long userId){
        return jdbcTemplate.query("CALL sp_get_tasks_by_user(?)",
                (rs, rowNum) -> {
                    // Get tags from BD
                    String tagsString = rs.getString("tags");
                    List<String> tagsList;
                    // Convert result to List
                    if (tagsString != null && !tagsString.isEmpty()) {
                        tagsList = Arrays.asList(tagsString.split(", "));
                    } else {
                        tagsList = Collections.emptyList();
                    }

                    return new TaskWithProject(
                            rs.getLong("id"),
                            rs.getLong("project_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getBoolean("is_completed"),
                            rs.getDate("due_date").toLocalDate(),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            rs.getString("project_name"),
                            rs.getString("project_description"),
                            rs.getTimestamp("project_created_at").toLocalDateTime(),
                            tagsList
                    );
                }, userId
        );
    }

    public List<TaskDetails> getTaskByProject(Long projectId){
        return jdbcTemplate.query("CALL sp_get_tasks_by_project(?)",
                (rs, rowNum) -> {
                    String tagsString = rs.getString("tags");
                    List<String> tagsList;

                    if (tagsString != null && !tagsString.isEmpty()) {
                        tagsList = Arrays.asList(tagsString.split(", "));
                    } else {
                        tagsList = Collections.emptyList();
                    }

                    return new TaskDetails(
                            rs.getLong("id"),
                            rs.getLong("project_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getBoolean("is_completed"),
                            rs.getDate("due_date").toLocalDate(),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            tagsList
                    );
                }, projectId
        );
    }
}
