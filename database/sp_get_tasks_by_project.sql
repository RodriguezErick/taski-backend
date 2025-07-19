DROP PROCEDURE IF EXISTS sp_get_tasks_by_project;
DELIMITER //
	CREATE PROCEDURE sp_get_tasks_by_project(
		IN p_project_id INT
    )
    BEGIN
		SELECT * FROM tbl_tasks WHERE project_id = p_project_id;
    END
// DELIMITER ;

















¡    
&*****   