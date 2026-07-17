package com.RideX.carpooling.services.implementation;

import com.RideX.carpooling.model.Rating;
import com.RideX.carpooling.model.User;
import com.RideX.carpooling.repositories.RatingRepository;
import com.RideX.carpooling.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RatingServiceImplementation implements RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    public Rating saveRating(Rating rating) {
        rating.setTimestamp(LocalDateTime.now());
        return ratingRepository.save(rating);
    }
    public List<Rating> getRatingsForUser(User user) {
        return ratingRepository.findByRated(user);
    }

    @Override
    public Map<Integer, Long> getStarCounts(User ratedUser) {
        List<Rating> ratings = ratingRepository.findByRated(ratedUser);

        // Count how many times each star value (1–5) appears
        return ratings.stream()
                .collect(Collectors.groupingBy(
                        Rating::getStars,
                        Collectors.counting()
                ));
    }

    @Override
    @Cacheable(value = "ratingStats", key = "#ratedUser.userId")
    public RatingStats getRatingStats(User ratedUser) {
        return ratingRepository.findSummaryByRated(ratedUser)
                .map(summary -> new RatingStats(
                        summary.getAverage() != null ? summary.getAverage() : 0.0,
                        summary.getTotal() != null ? summary.getTotal() : 0L
                ))
                .orElseGet(() -> new RatingStats(0.0, 0L));
    }

    @Override
    public double getAverageRating(User ratedUser) {
        return getRatingStats(ratedUser).average();
    }

    @Override
    public long getTotalRatings(User ratedUser) {
        return getRatingStats(ratedUser).total();
    }
}
