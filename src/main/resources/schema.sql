CREATE TABLE input_data (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(50),
                            status VARCHAR(20)
);

CREATE TABLE output_data (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             name VARCHAR(50),
                             processed_at TIMESTAMP
);
CREATE TABLE user_output (
                             user_id INT ,
                             id INT ,
                             title VARCHAR(255),
                             body TEXT,
                             processed_at TIMESTAMP
);
CREATE TABLE shedlock (
                          name VARCHAR(64) PRIMARY KEY,
                          lock_until TIMESTAMP,
                          locked_at TIMESTAMP,
                          locked_by VARCHAR(255)
);