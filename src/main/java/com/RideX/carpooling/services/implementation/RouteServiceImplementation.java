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

@Service
public class RouteServiceImplementation implements RouteService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String API_KEY = AppConstants.ROUTE_SERVICE_API_KEY;

    @Override
    public List<String> getDistanceAndDuration(String fromLatStr, String fromLonStr, String toLatStr, String toLonStr) {
        logger.info("RouteServiceImplementation :: getDistanceAndDuration(String fromLatStr:[{}], String fromLonStr:[{}], String toLatStr:[{}], String toLonStr:[{}])", fromLatStr, fromLonStr, toLatStr, toLonStr);
        String url = AppConstants.ROUTE_SERVICE_API_ENDPOINT;
        List<String> result = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        try {
            double fromLat = Double.parseDouble(fromLatStr);
            double fromLon = Double.parseDouble(fromLonStr);
            double toLat = Double.parseDouble(toLatStr);
            double toLon = Double.parseDouble(toLonStr);

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

            if (response.getStatusCode() == HttpStatus.OK) {
                JSONObject json = new JSONObject(response.getBody());
                JSONObject summary = json.getJSONArray("routes")
                        .getJSONObject(0)
                        .getJSONObject("summary");

                double distance = summary.getDouble("distance") / 1000.0;
                double duration = summary.getDouble("duration") / 60.0;

                result.add(String.format("%.2f", distance));
                result.add(String.format("%.0f", duration));
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }
}
