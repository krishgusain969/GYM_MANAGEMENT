/**
 * RecommendationHandler.java
 * 
 * PURPOSE: Smart workout recommendations for the Gym Management System
 * 
 * This file provides personalized workout recommendations based on user's
 * fitness level and preferences. It analyzes user profiles and suggests
 * appropriate workout routines and optimal timing for gym sessions.
 * 
 * RESPONSIBILITIES:
 * - Generate personalized workout recommendations
 * - Analyze user fitness levels and preferences
 * - Provide exercise suggestions based on fitness level
 * - Handle recommendation requests with user parameters
 * - Enable CORS for cross-origin requests
 * 
 * API ENDPOINTS HANDLED:
 * - GET /recommend?username=X -> Get recommendations for specific user
 * 
 * RECOMMENDATION LOGIC:
 * - Beginner: Light cardio, basic strength, stretching, bodyweight exercises
 * - Intermediate: Moderate cardio, intermediate strength, core work, mixed exercises
 * - Advanced: HIIT training, advanced strength, powerlifting, CrossFit circuits
 * 
 * AUTHOR: Gym Management System
 * VERSION: 1.0
 * DATABASE: SQLite (user profile data)
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
import java.util.Random;

/**
 * Handles workout recommendations based on user data
 */
public class RecommendationHandler implements HttpHandler {
    
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
        
        if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
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
                // Get user's fitness level
                String userQuery = "SELECT fitness_level FROM users WHERE username = ?";
                PreparedStatement userPs = conn.prepareStatement(userQuery);
                userPs.setString(1, username);
                ResultSet userRs = userPs.executeQuery();
                
                String fitnessLevel = "beginner"; // default
                if (userRs.next()) {
                    fitnessLevel = userRs.getString("fitness_level");
                }
                userRs.close();
                userPs.close();
                
                // Generate recommendations based on fitness level
                String[] recommendations;
                switch (fitnessLevel.toLowerCase()) {
                    case "beginner":
                        recommendations = new String[]{
                            "Light cardio - 20 minutes treadmill",
                            "Basic strength training - 3 sets of 10 reps",
                            "Stretching and flexibility exercises",
                            "Bodyweight exercises - squats, push-ups, planks"
                        };
                        break;
                    case "intermediate":
                        recommendations = new String[]{
                            "Moderate cardio - 30 minutes elliptical",
                            "Intermediate strength training - 4 sets of 12 reps",
                            "Core workout - abs and lower back",
                            "Mixed exercises - combine cardio and weights"
                        };
                        break;
                    case "advanced":
                        recommendations = new String[]{
                            "HIIT training - 45 minutes intense workout",
                            "Advanced strength training - 5 sets of 15 reps",
                            "Powerlifting exercises",
                            "CrossFit style circuit training"
                        };
                        break;
                    default:
                        recommendations = new String[]{
                            "General fitness routine",
                            "Balanced cardio and strength training",
                            "Flexibility and mobility work"
                        };
                }
                
                // Return random recommendation from the appropriate level
                Random random = new Random();
                String recommendation = recommendations[random.nextInt(recommendations.length)];
                
                String res = recommendation;
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
