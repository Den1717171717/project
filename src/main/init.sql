CREATE TABLE IF NOT EXISTS users (
id SERIAL PRIMARY KEY,
name VARCHAR(100) NOT NULL,
surname VARCHAR(100) NOT NULL,

is_deleted BOOLEAN DEFAULT FALSE ,

address VARCHAR(255),
phone_number VARCHAR(15),
email VARCHAR(100) UNIQUE,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS co_author (

                                         id SERIAL PRIMARY KEY,
                                         name VARCHAR(100) NOT NULL,
                                         email VARCHAR(100),
                                         is_from_university BOOLEAN
);

CREATE TABLE IF NOT EXISTS user_co_authors (
                                               id SERIAL PRIMARY KEY,
                                               user_id INT NOT NULL,
                                               co_author_id INT NOT NULL,
                                               FOREIGN KEY (user_id) REFERENCES users(id),
                                               FOREIGN KEY (co_author_id) REFERENCES co_author(id)
);
CREATE TABLE IF NOT EXISTS quarter (
                                        id SERIAL PRIMARY KEY,
                                        year INT NOT NULL,
                                        first_enumerator INT NOT NULL CHECK (first_enumerator BETWEEN 1 AND 4),
                                        data_size INT NOT NULL ,
                                        start_date DATE NOT NULL,
                                        end_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS application  (
                                            id SERIAL PRIMARY KEY,
                                            user_id INT NOT NULL,
                                            quarter INT,
                                            title VARCHAR(255),
                                            abbreviation VARCHAR(10),
                                            abstract TEXT,
                                            justification TEXT,
                                            expected_results TEXT,
                                            military_use TEXT,
                                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                            FOREIGN KEY (user_id) REFERENCES users(id),
                                            FOREIGN KEY (quarter) REFERENCES quarter(id)
);

CREATE TABLE IF NOT EXISTS hpc_server_usage (
                                                id SERIAL PRIMARY KEY,
                                                application_id INT NOT NULL UNIQUE,
                                                cores_needed INT,
                                                ram_needed INT,
                                                duration_needed INT,
                                                graphics_card_specifications TEXT,
                                                input_stream_rate INT,
                                                output_stream_rate INT,
                                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                                FOREIGN KEY (application_id) REFERENCES application(id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS results (
                                       id SERIAL PRIMARY KEY,
                                       application_id INT NOT NULL,
                                       result_data TEXT,
                                       data_size INT,
                                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                       FOREIGN KEY (application_id) REFERENCES application(id)
);




CREATE TABLE IF NOT EXISTS department (
                                          id SERIAL PRIMARY KEY,
                                          name VARCHAR(100)
);
CREATE TABLE IF NOT EXISTS user_departments (
id SERIAL PRIMARY KEY,
user_id INT NOT NULL,
department_id INT NOT NULL,
FOREIGN KEY (user_id) REFERENCES users(id),
FOREIGN KEY (department_id) REFERENCES department(id)
);


CREATE TABLE IF NOT EXISTS organization (
id SERIAL PRIMARY KEY,
name VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS user_organizations (
id SERIAL PRIMARY KEY,
user_id INT NOT NULL,
organization_id INT NOT NULL,
FOREIGN KEY (user_id) REFERENCES users(id),
FOREIGN KEY (organization_id) REFERENCES organization(id)
);


INSERT INTO users (id, name, surname, address, phone_number, email, created_at) VALUES
                                                                                    (1, 'John', 'Doe', '123 Main St, City, Country', '123-456-7890', 'john.doe@example.com', CURRENT_TIMESTAMP),
                                                                                    (2, 'Jane', 'Smith', '456 Oak St, City, Country', '987-654-3210', 'jane.smith@example.com', CURRENT_TIMESTAMP),
                                                                                    (3, 'Ivan', 'Petrov', '789 Pine St, City, Country', '+7-900-111-2222', 'ivan.petrov@example.com', CURRENT_TIMESTAMP);

INSERT INTO co_author (id, name, email, is_from_university) VALUES
                                                                (1, 'Alice Johnson', 'alice.johnson@university.com', TRUE),
                                                                (2, 'Bob White', 'bob.white@company.com', FALSE),
                                                                (3, 'Carol Lee', 'carol.lee@research.org', TRUE);

INSERT INTO user_co_authors (id, user_id, co_author_id) VALUES
                                                            (1, 1, 1),
                                                            (2, 1, 2),
                                                            (3, 2, 1),
                                                            (4, 3, 3);

INSERT INTO quarter (id, year, first_enumerator, data_size, start_date, end_date) VALUES
                                                                                     (1, 2026, 1, 500, '2026-01-01', '2026-03-31'),
                                                                                     (2, 2026, 2, 600, '2026-04-01', '2026-06-30');

INSERT INTO department (name) VALUES
                                      ('Engineering'),
                                      ( 'HR'),
                                      ( 'Research');

INSERT INTO user_departments (id, user_id, department_id) VALUES
                                                              (1, 1, 1),
                                                              (2, 2, 2),
                                                              (3, 3, 3);

INSERT INTO organization (id, name) VALUES
                                        (1, 'ABC Corp'),
                                        (2, 'XYZ Inc'),
                                        (3, 'University Lab');

INSERT INTO user_organizations (id, user_id, organization_id) VALUES
                                                                  (1, 1, 1),
                                                                  (2, 2, 2),
                                                                  (3, 3, 3);

INSERT INTO application (id, user_id, quarter, title, abbreviation, abstract, justification, expected_results, military_use, created_at) VALUES
                                                                                                                                                 (1, 1, 1, 'High Performance Computing Task', 'HPC-1', 'A task involving high-performance computing resources.', 'For research purposes', 'Better performance of calculations', 'No', CURRENT_TIMESTAMP),
                                                                                                                                                 (2, 2, 2, 'Data Analysis Task', 'DAT-2', 'A task for analyzing large datasets.', 'For business purposes', 'Improved decision making', 'No', CURRENT_TIMESTAMP),
                                                                                                                                                 (3, 3, 1, 'Research Simulation', 'SIM-3', 'Simulation of complex models.', 'Academic research', 'Publishable results', 'No', CURRENT_TIMESTAMP);

INSERT INTO hpc_server_usage (id, application_id, cores_needed, ram_needed, duration_needed, graphics_card_specifications, input_stream_rate, output_stream_rate, created_at) VALUES
                                                                                                                                                                                  (1, 1, 16, 64, 48, 'NVIDIA Tesla V100', 1000, 2000, CURRENT_TIMESTAMP),
                                                                                                                                                                                  (2, 3, 32, 128, 72, 'NVIDIA A100', 2000, 4000, CURRENT_TIMESTAMP);


INSERT INTO results (id, application_id, result_data, data_size, created_at) VALUES
                                                                                 (1, 1, 'Task completed successfully. Performance improved by 20%.', 500, CURRENT_TIMESTAMP),
                                                                                 (2, 2, 'Data analysis completed. Results ready for review.', 300, CURRENT_TIMESTAMP),
                                                                                 (3, 3, 'Simulation produced expected behavior; datasets stored.', 1200, CURRENT_TIMESTAMP);





