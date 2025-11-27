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

INSERT INTO courses (title, level, description) VALUES
                                                    ('Introduction to Java', 'Beginner',
                                                     'Basic syntax, data types and control structures for new developers.'),
                                                    ('AI for Personalized Learning', 'Intermediate',
                                                     'Principles of adaptive learning systems and recommender algorithms.'),
                                                    ('User Interface Design for e-Learning', 'Intermediate',
                                                     'Designing usable and accessible interfaces for online education.');

INSERT INTO quiz_questions (question, option_a, option_b, option_c, option_d, correct_option) VALUES
                                                                                                  ('What is the main goal of an adaptive learning platform?',
                                                                                                   'To show the same content to all students',
                                                                                                   'To personalize learning paths based on student data',
                                                                                                   'To replace teachers completely',
                                                                                                   'To reduce the number of courses',
                                                                                                   'B'),
                                                                                                  ('Which data can be used for personalization in PalpAI?',
                                                                                                   'Only age of the student',
                                                                                                   'Only final exam score',
                                                                                                   'Progress, quiz results and behavior in the system',
                                                                                                   'Random values',
                                                                                                   'C'),
                                                                                                  ('What technology is mainly responsible for adapting content in PalpAI?',
                                                                                                   'Manual configuration by the administrator',
                                                                                                   'Artificial intelligence and recommendation algorithms',
                                                                                                   'Only static HTML pages',
                                                                                                   'Paper-based questionnaires',
                                                                                                   'B'),
                                                                                                  ('Which UI feature helps different users feel comfortable in the system?',
                                                                                                   'Fixed small font size for everyone',
                                                                                                   'Adaptive UI settings such as theme and font size',
                                                                                                   'Removing all settings from the interface',
                                                                                                   'Disabling dark mode',
                                                                                                   'B'),
                                                                                                  ('Why is real-time analytics important for adaptive learning?',
                                                                                                   'It makes the database larger',
                                                                                                   'It allows students to play games',
                                                                                                   'It allows the system to react quickly to student performance',
                                                                                                   'It has no real benefits',
                                                                                                   'C');
