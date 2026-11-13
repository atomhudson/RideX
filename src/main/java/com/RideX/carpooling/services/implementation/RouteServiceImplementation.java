package com.RideX.carpooling.services.implementation;

import com.RideX.carpooling.helpers.AppConstants;
import com.RideX.carpooling.services.RouteService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import java.util.Locale;

@Service
public class RouteServiceImplementation implements RouteService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String API_KEY = AppConstants.ROUTE_SERVICE_API_KEY;

    @Override
    public List<String> getDistanceAndDuration(String fromLatStr, String fromLonStr, String toLatStr, String toLonStr) {
        String url = AppConstants.ROUTE_SERVICE_API_ENDPOINT;
        List<String> result = new ArrayList<>();

        if (fromLatStr == null || fromLonStr == null || toLatStr == null || toLonStr == null ||
            fromLatStr.trim().isEmpty() || fromLonStr.trim().isEmpty() || 
            toLatStr.trim().isEmpty() || toLonStr.trim().isEmpty()) {
            logger.warn("Missing coordinates for distance/duration calculation: [{}, {}] to [{}, {}]",
                    fromLatStr, fromLonStr, toLatStr, toLonStr);
            return result;
        }

        double fromLat;
        double fromLon;
        double toLat;
        double toLon;

        try {
            fromLat = Double.parseDouble(fromLatStr.trim());
            fromLon = Double.parseDouble(fromLonStr.trim());
            toLat = Double.parseDouble(toLatStr.trim());
            toLon = Double.parseDouble(toLonStr.trim());
        } catch (NumberFormatException e) {
            logger.error("Failed to parse coordinate numbers: {}", e.getMessage());
            return result;
        }

        try {
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setConnectTimeout(3000);
            factory.setReadTimeout(4000);
            RestTemplate restTemplate = new RestTemplate(factory);

            // Coordinates: [lon, lat]
            Map<String, Object> body = new HashMap<>();
            body.put("coordinates", Arrays.asList(
                    Arrays.asList(fromLon, fromLat),
                    Arrays.asList(toLon, toLat)
            ));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", API_KEY);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JSONObject json = new JSONObject(response.getBody());
                JSONObject summary = json.getJSONArray("routes")
                        .getJSONObject(0)
                        .getJSONObject("summary");

                double distance = summary.getDouble("distance") / 1000.0;
                double duration = summary.getDouble("duration") / 60.0;

                result.add(String.format(Locale.US, "%.2f", distance));
                result.add(String.format(Locale.US, "%.0f", duration));
                return result;
            }
        } catch (Exception e) {
            logger.warn("OpenRouteService API call failed or timed out ({}), falling back to Haversine calculation.", e.getMessage());
        }

        // Fallback: Haversine distance with road curvature factor (1.25)
        try {
            double distance = calculateHaversineDistance(fromLat, fromLon, toLat, toLon) * 1.25;
            double duration = Math.max(10.0, (distance / 55.0) * 60.0);

            result.add(String.format(Locale.US, "%.2f", distance));
            result.add(String.format(Locale.US, "%.0f", duration));
        } catch (Exception ex) {
            logger.error("Error in fallback distance calculation: {}", ex.getMessage());
        }

        return result;
    }

    private double calculateHaversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Earth radius in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
