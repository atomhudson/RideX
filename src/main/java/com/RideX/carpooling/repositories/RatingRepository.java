package com.RideX.carpooling.repositories;

import com.RideX.carpooling.model.Rating;
import com.RideX.carpooling.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByRated(User rated);
    boolean existsByRaterAndRated(User rater, User rated);
    long countByRated(User rated);
}
