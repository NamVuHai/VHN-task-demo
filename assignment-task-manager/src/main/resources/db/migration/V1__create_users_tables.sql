CREATE TABLE users (
                       user_id UUID PRIMARY KEY,
                       user_name VARCHAR(255),
                       full_name VARCHAR(255),
                       password VARCHAR(255),
                       created_at TIMESTAMP NOT NULL,
                       updated_at TIMESTAMP NOT NULL,
                       create_by VARCHAR(255) NOT NULL
);
