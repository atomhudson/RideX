package com.RideX.carpooling.repositories;

import com.RideX.carpooling.dto.RatingSummaryProjection;
import com.RideX.carpooling.model.Rating;
import com.RideX.carpooling.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByRated(User rated);
    boolean existsByRaterAndRated(User rater, User rated);
    long countByRated(User rated);

    @Query("""
            SELECT COALESCE(AVG(r.stars), 0.0) AS average, COUNT(r) AS total
            FROM Rating r
            WHERE r.rated = :user
            """)
    Optional<RatingSummaryProjection> findSummaryByRated(@Param("user") User user);
}
