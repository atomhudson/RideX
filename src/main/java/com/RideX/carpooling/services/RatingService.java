package com.RideX.carpooling.services;

import com.RideX.carpooling.model.Rating;
import com.RideX.carpooling.model.User;

import java.util.List;
import java.util.Map;

public interface RatingService {
    record RatingStats(double average, long total) {}

    Rating saveRating(Rating rating);
    List<Rating> getRatingsForUser(User user);
    Map<Integer, Long> getStarCounts(User ratedUser);
    RatingStats getRatingStats(User ratedUser);
    double getAverageRating(User ratedUser);
    long getTotalRatings(User ratedUser);
}
