/**
 * DatabaseConnection.java
 * 
 * PURPOSE: SQLite database connection and initialization for the Gym Management System
 * 
 * This file handles all database operations including establishing connections,
 * creating database tables, and initializing sample data. It uses SQLite as the
 * database engine which provides a simple, file-based database solution that
 * doesn't require separate database server setup.
 * 
 * RESPONSIBILITIES:
 * - Load SQLite JDBC driver
 * - Establish database connections
 * - Create database tables if they don't exist
 * - Initialize sample data for testing
 * - Handle connection errors gracefully
 * 
 * DATABASE STRUCTURE:
 * - Users table: stores user accounts and profile information
 * - Workout_splits table: stores user workout schedules
 * - Auto-creates database file: gym_management.db
 * 
 * AUTHOR: Gym Management System
 * VERSION: 1.0
 * DATABASE: SQLite
 * JDBC DRIVER: sqlite-jdbc-3.45.1.0.jar
 */

package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Handles SQLite database connection and initialization
 * 
 * This class provides a centralized way to connect to the SQLite database
 * and ensures all necessary tables are created when the application starts.
 */
public class DatabaseConnection {
    // SQLite database file path - creates file in project root directory
    private static final String URL = "jdbc:sqlite:gym_management.db";

    /**
     * Get active connection to the SQLite database
     * 
     * This method loads the SQLite driver and establishes a connection to the
     * database file. If this is the first connection, it will also initialize
     * the database schema and sample data.
     * 
     * @return Active database connection
     * @throws SQLException If connection fails or driver not found
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Load SQLite JDBC driver (required for database connection)
            Class.forName("org.sqlite.JDBC");
            
            // Establish connection to SQLite database file
            Connection conn = DriverManager.getConnection(URL);
            System.out.println("SQLite database connection successful!");
            
            // Initialize database schema and sample data on first connection
            initializeDatabase(conn);
            
            return conn;
        } catch (ClassNotFoundException e) {
            // Handle case where SQLite driver is not available
            System.err.println("SQLite Driver not found! Ensure sqlite-jdbc.jar is in the classpath.");
            throw new SQLException("SQLite Driver not found. Please ensure sqlite-jdbc-3.45.1.0.jar is in the classpath.");
        } catch (SQLException e) {
            // Handle database connection errors
            System.err.println("SQLite connection failed: " + e.getMessage());
            throw new SQLException("SQLite database connection error: " + e.getMessage());
        }
    }

    /**
     * Initialize database schema and sample data
     * 
     * This method creates the necessary tables if they don't exist and
     * inserts sample data for testing purposes. It uses CREATE TABLE IF NOT EXISTS
     * to avoid errors on subsequent runs.
     * 
     * @param conn Active database connection
     * @throws SQLException If table creation or data insertion fails
     */
    private static void initializeDatabase(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            // Create users table for storing user accounts
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +  // Auto-incrementing primary key
                        "username TEXT UNIQUE," +                   // Unique username (login identifier)
                        "password TEXT," +                          // User password (stored as plain text for demo)
                        "name TEXT," +                              // User's full name
                        "age INTEGER," +                            // User's age
                        "membership TEXT," +                        // Membership type (monthly, quarterly, annual)
                        "fitness_level TEXT)");                     // Fitness level (beginner, intermediate, advanced)
            
            // Create workout_splits table for storing user workout schedules
            stmt.execute("CREATE TABLE IF NOT EXISTS workout_splits (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +  // Auto-incrementing primary key
                        "username TEXT," +                          // Foreign key reference to users.username
                        "day_of_week TEXT," +                       // Day of the week for workout
                        "body_part TEXT," +                         // Body part to train (chest, back, legs, etc.)
                        "preferred_time TEXT)");                    // Preferred time slot (morning, afternoon, evening)
            
            // Insert sample users for testing (only if they don't already exist)
            stmt.execute("INSERT OR IGNORE INTO users (username, password, name, age, membership, fitness_level) VALUES " +
                       "('john_doe', 'pass123', 'John Doe', 25, 'monthly', 'intermediate'), " +
                       "('jane_smith', 'pass456', 'Jane Smith', 28, 'annual', 'advanced')");
            
            // Insert sample workout splits for testing (only if they don't already exist)
            stmt.execute("INSERT OR IGNORE INTO workout_splits (username, day_of_week, body_part, preferred_time) VALUES " +
                       "('john_doe', 'Monday', 'Chest', 'Morning 6-10AM'), " +
                       "('john_doe', 'Tuesday', 'Back', 'Afternoon 12-4PM'), " +
                       "('jane_smith', 'Monday', 'Chest', 'Morning 6-10AM'), " +
                       "('jane_smith', 'Monday', 'Legs', 'Evening 5-9PM')");
            
            System.out.println("SQLite database initialized successfully!");
        }
    }
}
