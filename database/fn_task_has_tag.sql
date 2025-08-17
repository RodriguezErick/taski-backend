DROP FUNCTION IF EXISTS fn_task_has_tag;
DELIMITER $$
	CREATE FUNCTION fn_task_has_tag(p_task_id INT, p_tag_id INT)
	RETURNS BOOLEAN
	NOT DETERMINISTIC READS SQL DATA
    BEGIN
		DECLARE has_tag BOOLEAN;
        
        SELECT COUNT(*) > 0 INTO has_tag FROM tbl_task_tags WHERE task_id = p_task_id AND tag_id = p_tag_id;
        
        RETURN has_tag;
    END
$$ DELIMITER ;
