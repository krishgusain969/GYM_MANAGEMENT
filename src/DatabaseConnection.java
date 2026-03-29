package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Handles SQLite database connection
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:gym_management.db";

    /**
     * Get active connection to the SQLite database
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Load SQLite driver
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(URL);
            System.out.println("SQLite database connection successful!");
            initializeDatabase(conn);
            return conn;
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite Driver not found! Ensure sqlite-jdbc.jar is in the classpath.");
            throw new SQLException("SQLite Driver not found. Please ensure sqlite-jdbc-3.45.1.0.jar is in the classpath.");
        } catch (SQLException e) {
            System.err.println("SQLite connection failed: " + e.getMessage());
            throw new SQLException("SQLite database connection error: " + e.getMessage());
        }
    }

    private static void initializeDatabase(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            // Create users table
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "username TEXT UNIQUE," +
                        "password TEXT," +
                        "name TEXT," +
                        "age INTEGER," +
                        "membership TEXT," +
                        "fitness_level TEXT)");
            
            // Create workout_splits table
            stmt.execute("CREATE TABLE IF NOT EXISTS workout_splits (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "username TEXT," +
                        "day_of_week TEXT," +
                        "body_part TEXT," +
                        "preferred_time TEXT)");
            
            // Insert sample data if tables are empty
            stmt.execute("INSERT OR IGNORE INTO users (username, password, name, age, membership, fitness_level) VALUES " +
                       "('john_doe', 'pass123', 'John Doe', 25, 'monthly', 'intermediate'), " +
                       "('jane_smith', 'pass456', 'Jane Smith', 28, 'annual', 'advanced')");
            
            System.out.println("SQLite database initialized successfully!");
        }
    }
}
