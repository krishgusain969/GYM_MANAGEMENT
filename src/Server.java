package src;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Server {
    /**
     * Initializes and starts the Web Server on port 8080
     */
    public static void startServer() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            
            // Context paths to route API requests to their respective Java Handlers
            server.createContext("/", new StaticFileHandler());
            server.createContext("/register", new UserHandler.RegisterHandler());
            server.createContext("/login", new UserHandler.LoginHandler());
            server.createContext("/split", new SplitHandler());
            server.createContext("/crowd", new CrowdHandler());
            server.createContext("/recommend", new RecommendationHandler());
            
            // Using default executor
            server.setExecutor(null); 
            server.start();
            System.out.println("-> Server is listening on http://localhost:8080");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Serves static HTML/JS/CSS files from frontend directory
     */
    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            // Default to index.html if no file specified
            if (path.equals("/")) {
                path = "/index.html"; 
            }
            
            File file = new File("frontend" + path);
            
            if (file.exists() && !file.isDirectory()) {
                exchange.sendResponseHeaders(200, file.length());
                OutputStream os = exchange.getResponseBody();
                FileInputStream fs = new FileInputStream(file);
                byte[] buffer = new byte[1024];
                int count;
                // Read chunks and stream to browser
                while ((count = fs.read(buffer)) != -1) {
                    os.write(buffer, 0, count);
                }
                fs.close();
                os.close();
            } else {
                // Return 404 response if the file isn't matching
                String response = "404 Not Found";
                exchange.sendResponseHeaders(404, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }
}
