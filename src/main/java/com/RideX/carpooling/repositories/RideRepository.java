package com.RideX.carpooling.repositories;

import com.RideX.carpooling.model.Rides;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Rides, String> {
    List<Rides> findByDriverUserId(String driverUserId);

    Rides findByRideId(String rideId);

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
