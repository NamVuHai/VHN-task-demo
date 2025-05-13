-- Create tasks table with single-table inheritance
CREATE TABLE tasks (
                       id UUID PRIMARY KEY,
                       task_type VARCHAR(50) NOT NULL,
                       name VARCHAR(255) NOT NULL,
                       status VARCHAR(50) NOT NULL,
                       type VARCHAR(50) NOT NULL,
                       description TEXT,
                       user_id UUID NOT NULL,
                       business_value VARCHAR(255),
                       deadline DATE,
                       severity VARCHAR(255),
                       steps_to_reproduce TEXT,
                       created_at TIMESTAMP NOT NULL,
                       updated_at TIMESTAMP NOT NULL,
                       create_by VARCHAR(255) NOT NULL,
                       CONSTRAINT fk_tasks_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);