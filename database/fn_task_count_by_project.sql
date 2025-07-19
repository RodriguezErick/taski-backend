DROP FUNCTION IF EXISTS fn_task_count_by_project;
DELIMITER $$
	CREATE FUNCTION fn_task_count_by_project(
		p_project_id INT
    )
    RETURNS INT
    NOT DETERMINISTIC READS SQL DATA
    BEGIN
		DECLARE task_count INT;
        
        SELECT COUNT(*) INTO task_count FROM tbl_tasks WHERE project_id = p_project_id;
        
        RETURN task_count;
        
    END
$$ DELIMITER ;