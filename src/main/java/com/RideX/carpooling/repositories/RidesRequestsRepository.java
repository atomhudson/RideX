package com.RideX.carpooling.repositories;

import com.RideX.carpooling.model.RideRequest;
import com.RideX.carpooling.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RidesRequestsRepository extends JpaRepository<RideRequest, String> {
    List<RideRequest> findAllByUser_UserId(String userId);

    @Query("SELECT rr FROM RideRequest rr JOIN FETCH rr.ride WHERE rr.user = :user")
    List<RideRequest> findAllByUserWithRide(@Param("user") User user);
}
