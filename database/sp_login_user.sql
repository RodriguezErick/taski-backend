DROP PROCEDURE IF EXISTS sp_login_user;
DELIMITER //
CREATE PROCEDURE sp_login_user(
    IN p_email VARCHAR(100),
    IN p_password_hash VARCHAR(250)
)
BEGIN
	SELECT id, username, email
    FROM tbl_users WHERE
    email = p_email AND password_hash = p_password_hash;
END;
// DELIMITER ;