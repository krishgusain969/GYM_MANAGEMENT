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
 * Handles workout split management
 */
public class SplitHandler implements HttpHandler {
    
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

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        
        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            Map<String, String> params = parseBody(exchange);
            
            try (Connection conn = DatabaseConnection.getConnection()) {
                String query = "INSERT INTO workout_splits (username, day_of_week, body_part, preferred_time) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, params.get("username"));
                ps.setString(2, params.get("day_of_week"));
                ps.setString(3, params.get("body_part"));
                ps.setString(4, params.get("preferred_time"));
                ps.executeUpdate();
                
                String res = "Split added successfully";
                exchange.sendResponseHeaders(200, res.length());
                OutputStream os = exchange.getResponseBody();
                os.write(res.getBytes());
                os.close();
            } catch (Exception e) {
                String res = "Error: " + e.getMessage();
                exchange.sendResponseHeaders(500, res.length());
                OutputStream os = exchange.getResponseBody();
                os.write(res.getBytes());
                os.close();
            }
        } else if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            String query = exchange.getRequestURI().getQuery();
            String username = null;
            
            if (query != null) {
                String[] params = query.split("&");
                for (String param : params) {
                    String[] kv = param.split("=");
                    if (kv.length == 2 && "username".equals(kv[0])) {
                        username = URLDecoder.decode(kv[1], "UTF-8");
                        break;
                    }
                }
            }
            
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "SELECT day_of_week, body_part, preferred_time FROM workout_splits WHERE username = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                
                StringBuilder response = new StringBuilder();
                while (rs.next()) {
                    response.append(rs.getString("day_of_week")).append("|");
                    response.append(rs.getString("body_part")).append("|");
                    response.append(rs.getString("preferred_time")).append("\n");
                }
                
                String res = response.toString();
                exchange.sendResponseHeaders(200, res.length());
                OutputStream os = exchange.getResponseBody();
                os.write(res.getBytes());
                os.close();
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
