DROP PROCEDURE IF EXISTS sp_create_task;
DELIMITER //
	CREATE PROCEDURE sp_create_task(
		IN p_project_id INT,
        IN p_title VARCHAR(100),
        IN p_description TEXT,
        IN p_due_date DATE
    )
    BEGIN
		INSERT INTO tbl_tasks(project_id, title, description, due_date)
        VALUES (p_project_id, p_title, p_description, p_due_date);
    END
// DELIMITER ;