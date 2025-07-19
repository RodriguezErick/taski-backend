DROP PROCEDURE IF EXISTS sp_remove_tag_from_task;
DELIMITER //
	CREATE PROCEDURE sp_remove_tag_from_task(
		IN p_task_id INT,
		IN p_tag_id INT
	)
	BEGIN
		DELETE FROM tbl_task_tags WHERE task_id = p_task_id AND tag_id = p_tag_id;
	END
// DELIMITER ;