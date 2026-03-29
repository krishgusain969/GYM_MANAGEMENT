-- SQLite Database Setup for Gym Management System
-- This file is for reference only - the database is auto-created by the application

-- Table 1: users
CREATE TABLE IF NOT EXISTS users (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  username TEXT UNIQUE,
  password TEXT,
  name TEXT,
  age INTEGER,
  membership TEXT,
  fitness_level TEXT
);

-- Table 2: workout_splits
CREATE TABLE IF NOT EXISTS workout_splits (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  username TEXT,
  day_of_week TEXT,
  body_part TEXT,
  preferred_time TEXT
);

-- Sample inserts for users to see data right away
INSERT OR IGNORE INTO users (username, password, name, age, membership, fitness_level) VALUES
('john_doe', 'pass123', 'John Doe', 25, 'monthly', 'intermediate'),
('jane_smith', 'pass456', 'Jane Smith', 28, 'annual', 'advanced');

-- Sample inserts for splits
INSERT OR IGNORE INTO workout_splits (username, day_of_week, body_part, preferred_time) VALUES
('john_doe', 'Monday', 'Chest', 'Morning 6-10AM'),
('john_doe', 'Tuesday', 'Back', 'Afternoon 12-4PM'),
('jane_smith', 'Monday', 'Chest', 'Morning 6-10AM'),
('jane_smith', 'Monday', 'Legs', 'Evening 5-9PM');
