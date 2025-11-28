CREATE TABLE courses (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         title VARCHAR(255) NOT NULL,
                         level VARCHAR(100),
                         description VARCHAR(1000)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE quiz_questions (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                question VARCHAR(1000) NOT NULL,
                                option_a VARCHAR(500),
                                option_b VARCHAR(500),
                                option_c VARCHAR(500),
                                option_d VARCHAR(500),
                                correct_option CHAR(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;