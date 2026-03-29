package src;

/**
 * Starts the application
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("  SMART GYM MANAGEMENT SYSTEM");
        System.out.println("=================================");
        System.out.println("Database: MySQL (via JDBC)");
        System.out.println("Server  : Java Built-in HttpServer");
        System.out.println("\nEnsure your MySQL database `gym_db` is created and running.");
        System.out.println("Starting server...");
        
        // Start handling HTTP requests
        Server.startServer();
    }
}
