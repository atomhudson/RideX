package com.RideX.carpooling.services;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
public class ProfileCacheService {

    @CacheEvict(value = {"profileRides", "ratingStats"}, key = "#userId")
    public void evictForUser(String userId) {
    }

    @Caching(evict = {
            @CacheEvict(value = "profileRides", key = "#userId"),
            @CacheEvict(value = "ratingStats", key = "#userId"),
            @CacheEvict(value = "navbarUser", key = "#email"),
            @CacheEvict(value = "profileUser", key = "#email")
    })
    public void evictForUser(String userId, String email) {
    }

    @CacheEvict(value = {"navbarUser", "profileUser", "profileRides", "ratingStats", "rideDetail"}, allEntries = true)
    public void evictAll() {
    }
}
