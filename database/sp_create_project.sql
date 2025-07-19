DROP PROCEDURE IF EXISTS sp_create_project;
DELIMITER //
CREATE PROCEDURE sp_create_project(
    IN p_user_id INT,
    IN p_name VARCHAR(100),
    IN p_description TEXT
)
BEGIN
	INSERT INTO tbl_projects (user_id, name, description)
    VALUES (p_user_id, p_name, p_description);
END;
// DELIMITER ;