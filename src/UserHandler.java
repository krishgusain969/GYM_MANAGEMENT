/**
 * UserHandler.java
 * 
 * PURPOSE: User authentication and account management for the Gym Management System
 * 
 * This file handles all user-related operations including registration, login validation,
 * and account management. It processes HTTP requests for user authentication endpoints
 * and interacts with the SQLite database to store and retrieve user information.
 * 
 * RESPONSIBILITIES:
 * - Process user registration requests
 * - Validate user login credentials
 * - Handle duplicate username detection
 * - Manage user profile data (name, age, membership, fitness level)
 * - Provide proper error handling and user feedback
 * - Enable CORS for cross-origin requests
 * 
 * API ENDPOINTS HANDLED:
 * - POST /register -> UserHandler.RegisterHandler (user registration)
 * - POST /login -> UserHandler.LoginHandler (user authentication)
 * - OPTIONS /register & /login -> CORS preflight requests
 * 
 * AUTHOR: Gym Management System
 * VERSION: 1.0
 * DATABASE: SQLite
 */

package src;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles user authentication and account management
 * 
 * This class provides the core functionality for user registration and login,
 * including form data parsing, database operations, and response handling.
 */
public class UserHandler {
    
    /**
     * Parses URL-encoded form data from HTTP request body
     * 
     * This method extracts form parameters from POST requests where the content
     * type is application/x-www-form-urlencoded. It handles URL decoding and
     * returns a map of parameter names and values.
     * 
     * @param exchange HTTP exchange object containing request information
     * @return Map of form parameter names and values
     * @throws IOException If request body reading fails
     */
    public static Map<String, String> parseBody(HttpExchange exchange) throws IOException {
        // Get input stream from request body
        InputStream is = exchange.getRequestBody();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder body = new StringBuilder();
        String line;
        
        // Read entire request body
        while ((line = reader.readLine()) != null) {
            body.append(line);
        }
        
        // Parse URL-encoded parameters (key=value&key2=value2)
        Map<String, String> params = new HashMap<>();
        String[] pairs = body.toString().split("&");
        for (String pair : pairs) {
            String[] kv = pair.split("=");
            if (kv.length == 2) {
                // URL decode parameter names and values
                params.put(URLDecoder.decode(kv[0], "UTF-8"), URLDecoder.decode(kv[1], "UTF-8"));
            }
        }
        return params;
    }

    /**
     * RegisterHandler - Handles user registration requests
     * 
     * This inner class processes POST requests to /register endpoint.
     * It validates user input, checks for duplicate usernames, and creates
     * new user accounts in the database.
     */
    public static class RegisterHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Enable CORS for cross-origin requests from frontend
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            
            // Handle POST requests for user registration
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                // Parse form data from request body
                Map<String, String> params = parseBody(exchange);
                
                try (Connection conn = DatabaseConnection.getConnection()) {
                    // Check if username already exists in database
                    String checkQuery = "SELECT username FROM users WHERE username = ?";
                    PreparedStatement checkPs = conn.prepareStatement(checkQuery);
                    checkPs.setString(1, params.get("username"));
                    ResultSet rs = checkPs.executeQuery();
                    
                    // If username exists, return error response
                    if (rs.next()) {
                        String res = "Username already exists";
                        exchange.sendResponseHeaders(400, res.length());
                        OutputStream os = exchange.getResponseBody();
                        os.write(res.getBytes());
                        os.close();
                        return;
                    }
                    rs.close();
                    checkPs.close();
                    
                    // Insert new user into database
                    String query = "INSERT INTO users (username, password, name, age, membership, fitness_level) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, params.get("username"));           // Username
                    ps.setString(2, params.get("password"));           // Password
                    ps.setString(3, params.get("name"));               // Full name
                    ps.setInt(4, Integer.parseInt(params.getOrDefault("age", "0"))); // Age
                    ps.setString(5, params.get("membership"));         // Membership type
                    ps.setString(6, params.get("fitness_level"));      // Fitness level
                    ps.executeUpdate();
                    ps.close();
                    
                    // Send success response
                    String res = "Registration successful";
                    exchange.sendResponseHeaders(200, res.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(res.getBytes());
                    os.close();
                    
                } catch (NumberFormatException e) {
                    // Handle invalid age format
                    System.err.println("Number format error in registration: " + e.getMessage());
                    String res = "Invalid age format. Please enter a valid number.";
                    exchange.sendResponseHeaders(400, res.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(res.getBytes());
                    os.close();
                    
                } catch (Exception e) {
                    // Handle database or other errors
                    System.err.println("Database error in registration: " + e.getMessage());
                    e.printStackTrace();
                    String res = "Registration failed. Please try again.";
                    exchange.sendResponseHeaders(500, res.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(res.getBytes());
                    os.close();
                }
            } else if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                // Handle CORS preflight requests
                exchange.sendResponseHeaders(204, -1);
            } else {
                // Handle unsupported HTTP methods
                exchange.sendResponseHeaders(405, -1);
            }
            exchange.close();
        }
    }
    
    /**
     * LoginHandler - Handles user authentication requests
     * 
     * This inner class processes POST requests to /login endpoint.
     * It validates user credentials against the database and returns
     * authentication results.
     */
    public static class LoginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Enable CORS for cross-origin requests from frontend
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            // Handle POST requests for user login
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                // Parse form data from request body
                Map<String, String> params = parseBody(exchange);
                String username = params.get("username");
                String password = params.get("password");
                
                try (Connection conn = DatabaseConnection.getConnection()) {
                    // Query database for user credentials
                    String query = "SELECT name FROM users WHERE username = ? AND password = ?";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, username);  // Username parameter
                    ps.setString(2, password);   // Password parameter
                    ResultSet rs = ps.executeQuery();
                    
                    // If credentials match, return success response
                    if (rs.next()) {
                        String name = rs.getString("name");
                        String res = "Login successful," + name;
                        exchange.sendResponseHeaders(200, res.getBytes("UTF-8").length);
                        OutputStream os = exchange.getResponseBody();
                        os.write(res.getBytes("UTF-8"));
                        os.close();
                    } else {
                        // If no match found, return authentication error
                        String res = "Invalid credentials";
                        exchange.sendResponseHeaders(401, res.length());
                        OutputStream os = exchange.getResponseBody();
                        os.write(res.getBytes());
                        os.close();
                    }
                } catch (Exception e) {
                    // Handle database or other errors
                    System.err.println("Database error in login: " + e.getMessage());
                    String res = "Error: " + e.getMessage();
                    exchange.sendResponseHeaders(500, res.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(res.getBytes());
                    os.close();
                }
            } else if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                // Handle CORS preflight requests
                exchange.sendResponseHeaders(204, -1);
            } else {
                // Handle unsupported HTTP methods
                exchange.sendResponseHeaders(405, -1);
            }
            exchange.close();
        }
    }
}
