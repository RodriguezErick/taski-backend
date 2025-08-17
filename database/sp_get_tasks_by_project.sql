DROP PROCEDURE IF EXISTS sp_get_tasks_by_project;
DELIMITER //
	CREATE PROCEDURE sp_get_tasks_by_project(
		IN p_project_id INT
    )
    BEGIN
		SELECT tasks.*, 
        GROUP_CONCAT(DISTINCT tags.name ORDER BY tags.name SEPARATOR ', ') AS tags
        FROM tbl_tasks tasks
        LEFT JOIN tbl_task_tags task_tg ON tasks.id = task_tg.task_id
        LEFT JOIN tbl_tags tags ON task_tg.tag_id = tags.id
        WHERE project_id = p_project_id
        GROUP BY tasks.id, tasks.title, tasks.description, tasks.is_completed, tasks.due_date;
    END
// DELIMITER ;
