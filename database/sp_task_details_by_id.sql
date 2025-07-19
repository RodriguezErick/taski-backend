DROP PROCEDURE IF EXISTS sp_task_details_by_id;
DELIMITER //
	CREATE PROCEDURE sp_task_details_by_id(
		IN p_task_id INT
    )
    BEGIN
		SELECT
        tasks.id AS task_id,
        tasks.title,
        tasks.description,
        tasks.is_completed,
        tasks.due_date,
        projects.id AS project_id,
        projects.name AS project_name,
        GROUP_CONCAT(DISTINCT tags.name ORDER BY tags.name SEPARATOR ', ') AS tags
        FROM tbl_tasks tasks
        INNER JOIN tbl_projects projects ON tasks.project_id = projects.id
        LEFT JOIN tbl_task_tags task_tg ON tasks.id = task_tg.task_id
        LEFT JOIN tbl_tags tags ON task_tg.tag_id = tags.id
        WHERE tasks.id = p_task_id
        GROUP BY tasks.id, tasks.title, tasks.description, tasks.is_completed, tasks.due_date, projects.id, projects.name; 
    END
// DELIMITER ;