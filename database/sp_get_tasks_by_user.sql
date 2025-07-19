DROP PROCEDURE IF EXISTS sp_get_tasks_by_user;
DELIMITER //
	CREATE PROCEDURE sp_get_tasks_by_user (
		IN p_user_id INT
    )
    BEGIN
    SELECT * FROM tbl_tasks tasks
    INNER JOIN  tbl_projects projects ON tasks.project_id = projects.id
    WHERE projects.user_id = p_user_id;
    END;
// DELIMITER ;
