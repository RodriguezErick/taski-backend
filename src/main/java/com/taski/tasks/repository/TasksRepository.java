package com.taski.tasks.repository;

import com.taski.projects.model.Project;
import com.taski.tasks.model.Task;
import com.taski.tasks.model.TaskWithProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
                (rs, rowNum) -> new TaskWithProject(
                        rs.getLong("id"),
                        rs.getLong("project_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getBoolean("is_completed"),
                        rs.getDate("due_date").toLocalDate(),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getString("project_name"),
                        rs.getString("project_description"),
                        rs.getTimestamp("project_created_at").toLocalDateTime()
                ), userId
        );
    }
}
