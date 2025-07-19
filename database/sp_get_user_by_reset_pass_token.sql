DROP PROCEDURE IF EXISTS sp_get_user_by_reset_pass_token;
DELIMITER //

CREATE PROCEDURE sp_get_user_by_reset_pass_token(
    IN p_token VARCHAR(100)
)
BEGIN
    SELECT 
    id,
    username,
    email,
    reset_token,
    reset_token_expiration
    FROM tbl_users WHERE reset_token = p_token;
END;
//

DELIMITER ;
