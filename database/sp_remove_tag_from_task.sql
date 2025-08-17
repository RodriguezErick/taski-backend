DROP PROCEDURE IF EXISTS sp_remove_tag_from_task;
DELIMITER //
CREATE PROCEDURE sp_remove_tag_from_task(
    IN p_task_id INT,
    IN p_tag_name VARCHAR(20)
)
BEGIN
    DECLARE v_tag_id INT;
    SELECT id INTO v_tag_id FROM tbl_tags WHERE name = p_tag_name;

    IF v_tag_id IS NOT NULL THEN
        DELETE FROM tbl_task_tags WHERE task_id = p_task_id AND tag_id = v_tag_id;
    END IF;
END
//
DELIMITER ;