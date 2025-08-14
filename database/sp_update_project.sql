DROP PROCEDURE IF EXISTS sp_update_project;
DELIMITER //
	CREATE PROCEDURE sp_update_project(
		IN p_id INT,
        IN p_name VARCHAR(100),
        IN p_description TEXT
    )
    BEGIN
		UPDATE tbl_projects
        SET name = p_name, description = p_description 
        WHERE id = p_id;
    END
// DELIMITER ;
