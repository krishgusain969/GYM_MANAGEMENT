/**
 * Main.java
 * 
 * PURPOSE: Entry point for the Gym Management System application
 * 
 * This file serves as the main starting point for the entire application.
 * It initializes and launches the web server that handles all HTTP requests
 * for the gym management system including user authentication, workout splits,
 * crowd analysis, and recommendations.
 * 
 * RESPONSIBILITIES:
 * - Display application startup banner
 * - Initialize the web server on port 8080
 * - Handle any startup errors gracefully
 * 
 * AUTHOR: Gym Management System
 * VERSION: 1.0
 * DATABASE: SQLite (via JDBC)
 * SERVER: Java Built-in HttpServer
 */

package src;

/**
 * Main application class that starts the Gym Management System
 */
public class Main {
    /**
     * Main method - application entry point
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Display application startup banner
        System.out.println("=================================");
        System.out.println("  SMART GYM MANAGEMENT SYSTEM");
        System.out.println("=================================");
        System.out.println("Database: SQLite (via JDBC)");
        System.out.println("Server  : Java Built-in HttpServer");
        System.out.println("\nStarting server...");
        
        // Start the web server to handle HTTP requests
        // This will start listening on port 8080 for all API endpoints
        Server.startServer();
    }
}
