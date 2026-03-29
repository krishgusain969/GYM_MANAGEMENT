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
 * Handles /login and /register paths
 */
public class UserHandler {
    
    // Reads URL-encoded parameters out of the fetch request body
    public static Map<String, String> parseBody(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder body = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            body.append(line);
        }
        
        Map<String, String> params = new HashMap<>();
        String[] pairs = body.toString().split("&");
        for (String pair : pairs) {
            String[] kv = pair.split("=");
            if (kv.length == 2) {
                params.put(URLDecoder.decode(kv[0], "UTF-8"), URLDecoder.decode(kv[1], "UTF-8"));
            }
        }
        return params;
    }

    public static class RegisterHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                Map<String, String> params = parseBody(exchange);
                
                try (Connection conn = DatabaseConnection.getConnection()) {
                    // Check if username already exists
                    String checkQuery = "SELECT username FROM users WHERE username = ?";
                    PreparedStatement checkPs = conn.prepareStatement(checkQuery);
                    checkPs.setString(1, params.get("username"));
                    ResultSet rs = checkPs.executeQuery();
                    
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
                    
                    // Insert new user
                    String query = "INSERT INTO users (username, password, name, age, membership, fitness_level) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, params.get("username"));
                    ps.setString(2, params.get("password"));
                    ps.setString(3, params.get("name"));
                    ps.setInt(4, Integer.parseInt(params.getOrDefault("age", "0")));
                    ps.setString(5, params.get("membership"));
                    ps.setString(6, params.get("fitness_level"));
                    ps.executeUpdate();
                    ps.close();
                    
                    String res = "Registration successful";
                    exchange.sendResponseHeaders(200, res.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(res.getBytes());
                    os.close();
                } catch (NumberFormatException e) {
                    String res = "Invalid age format";
                    exchange.sendResponseHeaders(400, res.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(res.getBytes());
                    os.close();
                } catch (Exception e) {
                    String res = "Registration failed. Please try again.";
                    exchange.sendResponseHeaders(500, res.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(res.getBytes());
                    os.close();
                }
            }
        }
    }
    
    public static class LoginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                Map<String, String> params = parseBody(exchange);
                String u = params.get("username");
                String p = params.get("password");
                
                try (Connection conn = DatabaseConnection.getConnection()) {
                    String query = "SELECT name FROM users WHERE username = ? AND password = ?";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setString(1, u);
                    ps.setString(2, p);
                    ResultSet rs = ps.executeQuery();
                    
                    if (rs.next()) {
                        String name = rs.getString("name");
                        String res = "Login successful," + name;
                        exchange.sendResponseHeaders(200, res.getBytes("UTF-8").length);
                        OutputStream os = exchange.getResponseBody();
                        os.write(res.getBytes("UTF-8"));
                        os.close();
                    } else {
                        String res = "Invalid credentials";
                        exchange.sendResponseHeaders(401, res.length());
                        OutputStream os = exchange.getResponseBody();
                        os.write(res.getBytes());
                        os.close();
                    }
                } catch (Exception e) {
                    String res = "Error: " + e.getMessage();
                    exchange.sendResponseHeaders(500, res.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(res.getBytes());
                    os.close();
                }
            }
        }
    }
}
