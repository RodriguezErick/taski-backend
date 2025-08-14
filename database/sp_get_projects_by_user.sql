DROP PROCEDURE IF EXISTS sp_get_projects_by_user;
DELIMITER //
CREATE PROCEDURE sp_get_projects_by_user(
    IN p_user_id INT
)
BEGIN
	SELECT projects.*, COUNT(tasks.id) AS task_count FROM tbl_projects projects 
    LEFT JOIN tbl_tasks tasks ON tasks.project_id = projects.id
    WHERE
    user_id = p_user_id
    GROUP BY
    projects.id;
END;
// DELIMITER ;