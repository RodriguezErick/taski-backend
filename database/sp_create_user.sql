DROP PROCEDURE IF EXISTS sp_create_user;
DELIMITER //

CREATE PROCEDURE sp_create_user(
    IN p_username VARCHAR(50),
    IN p_email VARCHAR(100),
    IN p_password_hash VARCHAR(250),
    IN p_email_verified BOOLEAN,
    IN p_verification_token VARCHAR(100)
)
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM tbl_users WHERE email = p_email
    ) THEN
        INSERT INTO tbl_users (username, email, password_hash, email_verified, verification_token)
        VALUES (p_username, p_email, p_password_hash, p_email_verified, p_verification_token);
    ELSE
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'This email is already registered for an existing user';
    END IF;
END;
//

DELIMITER ;
