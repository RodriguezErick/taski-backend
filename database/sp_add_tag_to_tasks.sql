DROP PROCEDURE IF EXISTS sp_add_tag_to_tasks;
DELIMITER //
	CREATE PROCEDURE sp_add_tag_to_tasks(
		IN p_task_id INT,
        IN p_tag_name INT,
        OUT p_message VARCHAR(50)
    )
    BEGIN
		DECLARE p_tag_id INT;
        DECLARE p_task_has_tag BOOLEAN;
    
		CALL sp_get_or_create_tag(p_tag_name, p_tag_id);
        
        SET p_task_has_tag = fn_task_has_tag(p_task_id, p_tag_id);
        
        IF NOT p_task_has_tag THEN
			INSERT INTO tbl_task_tags (task_id, tag_id) VALUES (p_task_id, p_tag_id);
            SET p_message = 'Tag Added';
        ELSE
			SET p_message = 'Tag Already Exists';
        END IF;
    END;
// DELIMITER ;