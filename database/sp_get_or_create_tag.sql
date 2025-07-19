DROP PROCEDURE IF EXISTS sp_get_or_create_tag;
DELIMITER //
	CREATE PROCEDURE sp_get_or_create_tag(
		IN p_tag_name VARCHAR(50),
        OUT tag_id INT
    )
    BEGIN
		SELECT id INTO tag_id FROM tbl_tags WHERE name = p_tag_name
        LIMIT 1;
        
        IF tag_id IS NULL THEN
			INSERT INTO tbl_tags (name) VALUES (p_tag_name);
            SET tag_id = LAST_INSERT_ID();
            END IF;
    END
// DELIMITER ;