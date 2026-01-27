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
