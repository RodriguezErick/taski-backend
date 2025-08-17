DROP PROCEDURE IF EXISTS sp_get_tasks_by_user;
DELIMITER //
	CREATE PROCEDURE sp_get_tasks_by_user (
		IN p_user_id INT
    )
    BEGIN
    SELECT tasks.*,
    projects.name AS project_name,
    projects.description AS project_description,
    projects.created_at AS project_created_at,
    GROUP_CONCAT(DISTINCT tags.name ORDER BY tags.name SEPARATOR ', ') AS tags
    FROM tbl_tasks tasks
    INNER JOIN  tbl_projects projects ON tasks.project_id = projects.id
    LEFT JOIN tbl_task_tags task_tg ON tasks.id = task_tg.task_id
	LEFT JOIN tbl_tags tags ON task_tg.tag_id = tags.id
    WHERE projects.user_id = p_user_id
    GROUP BY tasks.id, tasks.title, tasks.description, tasks.is_completed, tasks.due_date;
    END;
// DELIMITER ;
