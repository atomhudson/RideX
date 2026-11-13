package com.RideX.carpooling.helpers;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * High-speed in-memory store for active driver GPS coordinates.
 * Provides instant sub-millisecond lookups for passenger polling and Leaflet animations.
 */
@Component
public class RideLocationStore {

    public static class LocationSnapshot {
        private final String rideId;
        private final double lat;
        private final double lon;
        private final double heading;
        private final double speed;
        private final long timestamp;

        public LocationSnapshot(String rideId, double lat, double lon, double heading, double speed, long timestamp) {
            this.rideId = rideId;
            this.lat = lat;
            this.lon = lon;
            this.heading = heading;
            this.speed = speed;
            this.timestamp = timestamp;
        }

        public String getRideId() { return rideId; }
        public double getLat() { return lat; }
        public double getLon() { return lon; }
        public double getHeading() { return heading; }
        public double getSpeed() { return speed; }
        public long getTimestamp() { return timestamp; }
    }

    private final Map<String, LocationSnapshot> liveLocations = new ConcurrentHashMap<>();

    public void updateLocation(String rideId, double lat, double lon, double heading, double speed) {
        liveLocations.put(rideId, new LocationSnapshot(rideId, lat, lon, heading, speed, System.currentTimeMillis()));
    }

    public LocationSnapshot getLocation(String rideId) {
        return liveLocations.get(rideId);
    }

    public void remove(String rideId) {
        liveLocations.remove(rideId);
    }
}
