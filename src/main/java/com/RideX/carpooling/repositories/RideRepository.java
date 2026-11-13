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

    Rides findByTrackingToken(String trackingToken);

    @Query("SELECT DISTINCT r FROM Rides r " +
           "LEFT JOIN FETCH r.driver " +
           "LEFT JOIN FETCH r.car " +
           "LEFT JOIN FETCH r.rideRequests rr " +
           "LEFT JOIN FETCH rr.user " +
           "WHERE r.rideId = :rideId")
    Rides findWithDetailsByRideId(@Param("rideId") String rideId);

    @Query("SELECT DISTINCT r FROM Rides r " +
            "LEFT JOIN FETCH r.driver " +
            "LEFT JOIN FETCH r.car " +
            "WHERE (:from IS NULL OR :from = '' OR LOWER(r.sourceCity) LIKE LOWER(CONCAT('%', :from, '%')) OR LOWER(r.sourceAddress) LIKE LOWER(CONCAT('%', :from, '%')) OR (r.sourceCity IS NOT NULL AND r.sourceCity <> '' AND LOWER(:from) LIKE LOWER(CONCAT('%', r.sourceCity, '%')))) " +
            "AND (:to IS NULL OR :to = '' OR LOWER(r.destinationCity) LIKE LOWER(CONCAT('%', :to, '%')) OR LOWER(r.destinationAddress) LIKE LOWER(CONCAT('%', :to, '%')) OR (r.destinationCity IS NOT NULL AND r.destinationCity <> '' AND LOWER(:to) LIKE LOWER(CONCAT('%', r.destinationCity, '%')))) " +
            "AND (:date IS NULL OR r.startTime >= :date)")
    List<Rides> findUpcomingRides(
            @Param("from") String from,
            @Param("to") String to,
            @Param("date") Date date,
            Sort sort);

    @Query("SELECT DISTINCT r FROM Rides r " +
            "LEFT JOIN FETCH r.driver " +
            "LEFT JOIN FETCH r.car " +
            "WHERE ((:from IS NOT NULL AND :from <> '' AND (LOWER(r.sourceCity) LIKE LOWER(CONCAT('%', :from, '%')) OR LOWER(r.sourceAddress) LIKE LOWER(CONCAT('%', :from, '%')) OR (r.sourceCity IS NOT NULL AND LOWER(:from) LIKE LOWER(CONCAT('%', r.sourceCity, '%'))))) " +
            "OR (:to IS NOT NULL AND :to <> '' AND (LOWER(r.destinationCity) LIKE LOWER(CONCAT('%', :to, '%')) OR LOWER(r.destinationAddress) LIKE LOWER(CONCAT('%', :to, '%')) OR (r.destinationCity IS NOT NULL AND LOWER(:to) LIKE LOWER(CONCAT('%', r.destinationCity, '%')))))) " +
            "AND (:date IS NULL OR r.startTime >= :date)")
    List<Rides> findUpcomingRidesFlexible(
            @Param("from") String from,
            @Param("to") String to,
            @Param("date") Date date,
            Sort sort);

    @Query("SELECT DISTINCT r FROM Rides r " +
            "LEFT JOIN FETCH r.driver " +
            "LEFT JOIN FETCH r.car " +
            "WHERE (:date IS NULL OR r.startTime >= :date)")
    List<Rides> findAllUpcomingWithDriver(@Param("date") Date date, Sort sort);

    List<Rides> findByRideStatusAndStartTimeBefore(String rideStatus, Date now);
    List<Rides> findByRideStatusAndEndTimeBefore(String rideStatus, Date now);
    
    Page<Rides> findByStartTimeBetween(Date startDate, Date endDate, Pageable pageable);
}
