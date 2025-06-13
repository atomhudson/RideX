package com.RideX.carpooling.services.implementation;

import com.RideX.carpooling.model.Rating;
import com.RideX.carpooling.model.User;
import com.RideX.carpooling.repositories.RatingRepository;
import com.RideX.carpooling.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public double getAverageRating(User ratedUser) {
        List<Rating> ratings = ratingRepository.findByRated(ratedUser);
        return ratings.stream()
                .mapToInt(Rating::getStars)
                .average()
                .orElse(0.0);
    }

    @Override
    public long getTotalRatings(User ratedUser) {
        return ratingRepository.countByRated(ratedUser);
    }
}
