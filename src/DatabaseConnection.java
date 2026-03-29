package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Handles MySQL database connection via JDBC
 */
public class DatabaseConnection {
    // MySQL credentials
    private static final String URL = "jdbc:mysql://localhost:3306/gym_db";
    // Change these if your MySQL credentials differ
    private static final String USER = "root"; 
    private static final String PASS = "password"; 

    /**
     * Get active connection to the MySQL database
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Explicitly load the driver (helpful for some classloaders)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL Driver not found! Ensure mysql-connector-jar is in the classpath.");
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
