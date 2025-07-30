DROP PROCEDURE IF EXISTS sp_get_user_by_id;
DELIMITER //

CREATE PROCEDURE sp_get_user_by_id(
    IN p_id INT
)
BEGIN
    SELECT 
        id,
        username,
        email,
        password_hash
    FROM tbl_users 
    WHERE id = p_id;
END;
//

DELIMITER ;
