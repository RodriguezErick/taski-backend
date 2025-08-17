DROP PROCEDURE IF EXISTS sp_add_tag_to_tasks;
DELIMITER //
CREATE PROCEDURE sp_add_tag_to_tasks(
    IN p_task_id INT,
    IN p_tag_name VARCHAR(50)
)
BEGIN
    DECLARE v_tag_id INT;
    DECLARE v_task_has_tag BOOLEAN;
    
    -- Call sp to get tag id in v_tag_id
    CALL sp_get_or_create_tag(p_tag_name, v_tag_id);
    
    SET v_task_has_tag = fn_task_has_tag(p_task_id, v_tag_id);
    
    IF NOT v_task_has_tag THEN
        INSERT INTO tbl_task_tags (task_id, tag_id) VALUES (p_task_id, v_tag_id);
        
        SELECT TRUE AS tag_added;
    ELSE
        SELECT FALSE AS tag_added;
    END IF;
END
//
DELIMITER ;