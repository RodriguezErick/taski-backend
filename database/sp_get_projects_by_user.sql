DROP PROCEDURE IF EXISTS sp_get_projects_by_user;
DELIMITER //
CREATE PROCEDURE sp_get_projects_by_user(
    IN p_user_id INT
)
BEGIN
	SELECT * FROM tbl_projects WHERE
    user_id = p_user_id;
END;
// DELIMITER ;