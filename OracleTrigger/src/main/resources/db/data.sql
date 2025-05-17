INSERT INTO users (id, name, email) VALUES (1, 'John Doe', 'john.doe@example.com');
INSERT INTO users (id, name, email) VALUES (2, 'Jane Smith', 'jane.smith@example.com');

INSERT INTO history (id, user_id, action, timestamp) VALUES (1, 1, 'INSERT', CURRENT_TIMESTAMP);
INSERT INTO history (id, user_id, action, timestamp) VALUES (2, 2, 'INSERT', CURRENT_TIMESTAMP);