CREATE DATABASE IF NOT EXISTS gym_db;
USE gym_db;

-- Table 1: users
CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) UNIQUE,
  password VARCHAR(100),
  name VARCHAR(100),
  age INT,
  membership VARCHAR(20),
  fitness_level VARCHAR(20)
);

-- Table 2: workout_splits
CREATE TABLE IF NOT EXISTS workout_splits (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50),
  day_of_week VARCHAR(10),
  body_part VARCHAR(30),
  preferred_time VARCHAR(20)
);

-- Sample inserts for users to see data right away
INSERT IGNORE INTO users (username, password, name, age, membership, fitness_level) VALUES
('john_doe', 'pass123', 'John Doe', 25, 'monthly', 'intermediate'),
('jane_smith', 'pass456', 'Jane Smith', 28, 'annual', 'advanced');

-- Sample inserts for splits
INSERT IGNORE INTO workout_splits (username, day_of_week, body_part, preferred_time) VALUES
('john_doe', 'Monday', 'Chest', 'Morning 6-10AM'),
('john_doe', 'Tuesday', 'Back', 'Afternoon 12-4PM'),
('jane_smith', 'Monday', 'Chest', 'Morning 6-10AM'),
('jane_smith', 'Monday', 'Legs', 'Evening 5-9PM');
