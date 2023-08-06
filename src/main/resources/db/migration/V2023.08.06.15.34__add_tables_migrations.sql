CREATE TABLE IF NOT EXISTS file_upload (
    id SERIAL PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL,
    created_time TIMESTAMP not null
);