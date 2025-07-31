package com.taski.projects.repository;

import com.taski.account.model.User;
import com.taski.projects.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProjectsRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Project> getProjectsByUserId(Long userId){
        return jdbcTemplate.query("CALL sp_get_projects_by_user(?)",
                (rs, rowNum) -> new Project(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ), userId
        );
    }
}
