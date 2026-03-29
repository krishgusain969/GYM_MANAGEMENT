// MongoDB Setup Script for Gym Management System
// Run this script in MongoDB shell to create collections and insert sample data
// Usage: mongo gym_db setup-mongodb.js

// Switch to gym_db database
use gym_db;

// Clear existing collections (for fresh setup)
db.users.drop();
db.workout_splits.drop();

print("Creating collections and inserting sample data...");

// Insert sample users into the users collection
db.users.insertMany([
    {
        username: "john_doe",
        password: "password123",
        name: "John Doe",
        age: 25,
        membership: "monthly",
        fitness_level: "intermediate"
    },
    {
        username: "jane_smith",
        password: "pass456",
        name: "Jane Smith",
        age: 30,
        membership: "annual",
        fitness_level: "advanced"
    },
    {
        username: "mike_wilson",
        password: "mike123",
        name: "Mike Wilson",
        age: 22,
        membership: "quarterly",
        fitness_level: "beginner"
    },
    {
        username: "sarah_jones",
        password: "sarah789",
        name: "Sarah Jones",
        age: 28,
        membership: "monthly",
        fitness_level: "intermediate"
    },
    {
        username: "alex_brown",
        password: "alex321",
        name: "Alex Brown",
        age: 35,
        membership: "annual",
        fitness_level: "advanced"
    }
]);

// Insert sample workout splits into the workout_splits collection
db.workout_splits.insertMany([
    // Monday workouts
    {
        username: "john_doe",
        day_of_week: "Monday",
        body_part: "Chest",
        preferred_time: "Morning"
    },
    {
        username: "jane_smith",
        day_of_week: "Monday",
        body_part: "Chest",
        preferred_time: "Morning"
    },
    {
        username: "mike_wilson",
        day_of_week: "Monday",
        body_part: "Back",
        preferred_time: "Afternoon"
    },
    {
        username: "sarah_jones",
        day_of_week: "Monday",
        body_part: "Legs",
        preferred_time: "Evening"
    },
    {
        username: "alex_brown",
        day_of_week: "Monday",
        body_part: "Chest",
        preferred_time: "Evening"
    },
    
    // Tuesday workouts
    {
        username: "john_doe",
        day_of_week: "Tuesday",
        body_part: "Back",
        preferred_time: "Morning"
    },
    {
        username: "jane_smith",
        day_of_week: "Tuesday",
        body_part: "Legs",
        preferred_time: "Morning"
    },
    {
        username: "mike_wilson",
        day_of_week: "Tuesday",
        body_part: "Chest",
        preferred_time: "Afternoon"
    },
    {
        username: "sarah_jones",
        day_of_week: "Tuesday",
        body_part: "Back",
        preferred_time: "Evening"
    },
    {
        username: "alex_brown",
        day_of_week: "Tuesday",
        body_part: "Shoulders",
        preferred_time: "Evening"
    },
    
    // Wednesday workouts
    {
        username: "john_doe",
        day_of_week: "Wednesday",
        body_part: "Legs",
        preferred_time: "Morning"
    },
    {
        username: "jane_smith",
        day_of_week: "Wednesday",
        body_part: "Shoulders",
        preferred_time: "Morning"
    },
    {
        username: "mike_wilson",
        day_of_week: "Wednesday",
        body_part: "Arms",
        preferred_time: "Afternoon"
    },
    {
        username: "sarah_jones",
        day_of_week: "Wednesday",
        body_part: "Chest",
        preferred_time: "Evening"
    },
    {
        username: "alex_brown",
        day_of_week: "Wednesday",
        body_part: "Back",
        preferred_time: "Evening"
    }
]);

// Create indexes for better performance
db.users.createIndex({ "username": 1 }, { unique: true });
db.workout_splits.createIndex({ "username": 1 });
db.workout_splits.createIndex({ "day_of_week": 1 });
db.workout_splits.createIndex({ "body_part": 1 });
db.workout_splits.createIndex({ "preferred_time": 1 });

print("MongoDB setup completed successfully!");
print("\nSample data inserted:");
print("Users: " + db.users.countDocuments() + " documents");
print("Workout Splits: " + db.workout_splits.countDocuments() + " documents");

print("\nSample users for testing:");
print("Username: john_doe, Password: password123");
print("Username: jane_smith, Password: pass456");
print("Username: mike_wilson, Password: mike123");
print("Username: sarah_jones, Password: sarah789");
print("Username: alex_brown, Password: alex321");

print("\nSetup complete! You can now start the Java application.");
