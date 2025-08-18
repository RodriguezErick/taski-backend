package com.taski.tags.repository;

import com.taski.tags.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TagsRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean addTagToTask(Tag tag){
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                "CALL sp_add_tag_to_tasks(?, ?)",
                Boolean.class,
                tag.getTaskId(),
                tag.getName()
        ));
    }

    public void removeTagFromTask(Long taskId, String tagName){
        jdbcTemplate.update("CALL sp_remove_tag_from_task(?, ?)", taskId, tagName);
    }
}
