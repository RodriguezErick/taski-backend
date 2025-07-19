DROP PROCEDURE IF EXISTS sp_delete_task;
DELIMITER //
	CREATE PROCEDURE sp_delete_task(
		IN p_task_id INT
    )
    BEGIN
		DELETE FROM tbl_tasks WHERE id = p_task_id;
    END
// DELIMITER ;