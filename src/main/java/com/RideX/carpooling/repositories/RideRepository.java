package com.RideX.carpooling.repositories;

import com.RideX.carpooling.model.Rides;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Rides, String> {
    List<Rides> findByDriverUserId(String driverUserId);

    @Query(value = "SELECT * FROM rides WHERE driver_id = :userId", nativeQuery = true)
    List<Rides> findByDriverIdOnly(@Param("userId") String userId);

    @Query(value = """
            SELECT DISTINCT r.* FROM rides r
            INNER JOIN ride_passengers rp ON r.ride_id = rp.ride_id
            WHERE rp.user_id = :userId
            """, nativeQuery = true)
    List<Rides> findByPassengerUserIdOnly(@Param("userId") String userId);

    @Query("SELECT r FROM Rides r JOIN r.passengers p WHERE p.userId = :userId")
    List<Rides> findByPassengerUserId(@Param("userId") String userId);

    Rides findByRideId(String rideId);

    @Query("SELECT DISTINCT r FROM Rides r " +
           "JOIN FETCH r.driver " +
           "JOIN FETCH r.car " +
           "LEFT JOIN FETCH r.rideRequests rr " +
           "LEFT JOIN FETCH rr.user " +
           "WHERE r.rideId = :rideId")
    Rides findWithDetailsByRideId(@Param("rideId") String rideId);

    @Query("SELECT r FROM Rides r " +
            "WHERE r.sourceCity LIKE CONCAT('%', :from, '%') " +
            "AND r.destinationCity LIKE CONCAT('%', :to, '%')" +
            "AND r.startTime > :date ")
    List<Rides> findUpcomingRides(
            @Param("from") String from,
            @Param("to") String to,
            @Param("date") Date date,
            Sort sort);

    List<Rides> findByRideStatusAndStartTimeBefore(String rideStatus, Date now);
    List<Rides> findByRideStatusAndEndTimeBefore(String rideStatus, Date now);
    
    Page<Rides> findByStartTimeBetween(Date startDate, Date endDate, Pageable pageable);
}
