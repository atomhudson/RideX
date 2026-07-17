package com.RideX.carpooling.services;

import com.RideX.carpooling.model.Rides;
import com.RideX.carpooling.repositories.RideRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class RideCacheService {

    private final RideRepository rideRepository;

    public RideCacheService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    @Cacheable(value = "rideDetail", key = "#rideId", unless = "#result == null")
    public Rides getRideWithDetails(String rideId) {
        return rideRepository.findWithDetailsByRideId(rideId);
    }

    @CacheEvict(value = "rideDetail", key = "#rideId")
    public void evictRideDetail(String rideId) {
    }
}
