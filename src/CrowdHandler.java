package src;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Handles crowd analysis and predictions
 */
public class CrowdHandler implements HttpHandler {
    
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
            
            if (query != null) {
                // Parse query parameters (day and time can be used for future enhancements)
                String[] params = query.split("&");
                for (String param : params) {
                    String[] kv = param.split("=");
                    if (kv.length == 2) {
                        // Parameters parsed but not currently used in simulation
                        // Can be used for more sophisticated crowd analysis
                    }
                }
            }
            
            try {
                // Simulate crowd analysis with random data
                Random random = new Random();
                int crowdLevel = random.nextInt(100) + 1; // 1-100
                
                String crowdStatus;
                if (crowdLevel < 30) {
                    crowdStatus = "Low";
                } else if (crowdLevel < 70) {
                    crowdStatus = "Moderate";
                } else {
                    crowdStatus = "High";
                }
                
                String res = crowdLevel + "|" + crowdStatus;
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
