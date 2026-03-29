/**
 * Server.java
 * 
 * PURPOSE: HTTP Server setup and routing for the Gym Management System
 * 
 * This file configures and starts the web server that handles all HTTP requests.
 * It sets up URL routing to different handler classes based on the endpoint paths.
 * The server serves both static files (HTML, CSS, JS) and API endpoints.
 * 
 * RESPONSIBILITIES:
 * - Create and configure HTTP server on port 8080
 * - Set up URL routing for all API endpoints
 * - Serve static files from the frontend directory
 * - Handle 404 errors for missing files
 * - Enable CORS for cross-origin requests
 * 
 * API ENDPOINTS HANDLED:
 * - /register -> UserHandler.RegisterHandler (user registration)
 * - /login -> UserHandler.LoginHandler (user authentication)
 * - /split -> SplitHandler (workout split management)
 * - /crowd -> CrowdHandler (crowd analysis)
 * - /recommend -> RecommendationHandler (workout recommendations)
 * - / -> StaticFileHandler (serves HTML/CSS/JS files)
 * 
 * AUTHOR: Gym Management System
 * VERSION: 1.0
 * PORT: 8080
 */

package src;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Main server class that handles HTTP requests and routing
 */
public class Server {
    
    /**
     * Initializes and starts the Web Server on port 8080
     * 
     * This method creates an HTTP server, sets up all the URL routes,
     * and starts listening for incoming requests. Each route is mapped
     * to a specific handler class that processes that type of request.
     */
    public static void startServer() {
        try {
            // Create HTTP server on port 8080
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            
            // Set up URL routing - map each path to its handler class
            // Static file handler serves HTML, CSS, and JavaScript files
            server.createContext("/", new StaticFileHandler());
            
            // API endpoints for user management
            server.createContext("/register", new UserHandler.RegisterHandler());
            server.createContext("/login", new UserHandler.LoginHandler());
            
            // API endpoints for gym features
            server.createContext("/split", new SplitHandler());
            server.createContext("/crowd", new CrowdHandler());
            server.createContext("/recommend", new RecommendationHandler());
            
            // Use default executor for handling requests
            server.setExecutor(null); 
            
            // Start the server and begin listening for requests
            server.start();
            System.out.println("-> Server is listening on http://localhost:8080");
        } catch (IOException e) {
            // Handle server startup errors
            e.printStackTrace();
        }
    }

    /**
     * StaticFileHandler - Serves static HTML/JS/CSS files from frontend directory
     * 
     * This handler processes requests for static files like index.html,
     * dashboard.html, style.css, etc. It looks for files in the frontend
     * directory and serves them with appropriate MIME types.
     */
    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Get the requested path from the URL
            String path = exchange.getRequestURI().getPath();
            
            // Default to index.html if no file specified (root path)
            if (path.equals("/")) {
                path = "/index.html"; 
            }
            
            // Handle URLs without extensions (add .html)
            else if (!path.contains(".")) {
                path = path + ".html";
            }
            
            // Create file object pointing to the requested file
            File file = new File("frontend" + path);
            
            // Check if file exists and is not a directory
            if (file.exists() && !file.isDirectory()) {
                // File found - serve it with 200 OK status
                exchange.sendResponseHeaders(200, file.length());
                OutputStream os = exchange.getResponseBody();
                
                // Stream the file content to the response
                FileInputStream fs = new FileInputStream(file);
                byte[] buffer = new byte[1024];
                int count;
                
                // Read file in chunks and write to response
                while ((count = fs.read(buffer)) != -1) {
                    os.write(buffer, 0, count);
                }
                
                // Clean up resources
                fs.close();
                os.close();
            } else {
                // File not found - return 404 error
                String response = "404 Not Found";
                exchange.sendResponseHeaders(404, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }
}
