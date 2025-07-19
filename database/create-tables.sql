CREATE TABLE IF NOT EXISTS tbl_users(
	id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    email_verified BOOLEAN DEFAULT FALSE,
    verification_token VARCHAR(100),
    reset_token VARCHAR(100),
	reset_token_expiration DATETIME
);

CREATE TABLE IF NOT EXISTS tbl_projects (
	id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES tbl_users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS tbl_tasks (
	id INT AUTO_INCREMENT PRIMARY KEY,
    project_id INT NOT NULl,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    is_completed BOOLEAN DEFAULT FALSE,
    due_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (project_id) REFERENCES tbl_projects(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS tbl_tags(
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS tbl_task_tags(
	task_id INT NOT NULL,
    tag_id INT NOT NULL,
    PRIMARY KEY (task_id, tag_id),
    FOREIGN KEY (task_id) REFERENCES tbl_tasks(id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tbl_tags(id) ON DELETE CASCADE
);