DROP PROCEDURE IF EXISTS sp_verify_email;
DELIMITER //

CREATE PROCEDURE sp_verify_email(
    IN p_token VARCHAR(100),
    OUT p_result INT
)
BEGIN
    UPDATE tbl_users
    SET email_verified = true,
        verification_token = null
    WHERE verification_token = p_token;

    SET p_result = ROW_COUNT();
END;
//

DELIMITER ;
