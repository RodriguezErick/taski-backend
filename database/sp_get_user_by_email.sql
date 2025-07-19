DROP PROCEDURE IF EXISTS sp_get_user_by_email;
DELIMITER //

CREATE PROCEDURE sp_get_user_by_email(
    IN p_email VARCHAR(100)
)
BEGIN
    SELECT 
        id,
        username,
        email,
        email_verified,
        verification_token,
        password_hash
    FROM tbl_users 
    WHERE email = p_email;
END;
//

DELIMITER ;
