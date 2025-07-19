DROP PROCEDURE IF EXISTS sp_update_user_password;
DELIMITER //

CREATE PROCEDURE sp_update_user_password(
    IN p_id BIGINT,
    IN p_new_password VARCHAR(250)
)
BEGIN
    
    IF EXISTS (SELECT 1 FROM tbl_users WHERE id = p_id) THEN
        
        UPDATE tbl_users
        SET password_hash = p_new_password,
            reset_token = NULL,
            reset_token_expiration = NULL
        WHERE id = p_id;
    ELSE
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'User not found for provided ID';
    END IF;
END;
//
DELIMITER ;
