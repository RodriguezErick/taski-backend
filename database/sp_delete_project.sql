DROP PROCEDURE IF EXISTS sp_delete_project;
DELIMITER //
	CREATE PROCEDURE sp_delete_project(
		IN p_project_id INT
    )
    BEGIN
		DELETE FROM tbl_projects WHERE id = p_project_id;
    END
// DELIMITER ;