DROP PROCEDURE IF EXISTS sp_update_task;
DELIMITER //
	CREATE PROCEDURE sp_update_task(
			IN p_id INT,
            IN p_title VARCHAR(100),
            IN p_is_completed BOOLEAN,
            IN p_description TEXT,
            IN p_due_date DATE,
            IN p_project_id INT
    )
    BEGIN
		UPDATE tbl_tasks
        SET title = p_title, description = p_description, is_completed = p_is_completed, due_date = p_due_date, project_id = p_project_id
        WHERE id = p_id;
    END
// DELIMITER ;