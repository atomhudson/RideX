package com.RideX.carpooling.repositories;

import com.RideX.carpooling.model.CarDetails;
import com.RideX.carpooling.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarDetailsRepository extends JpaRepository<CarDetails, String> {
    Optional<CarDetails> findByCarNumber(String carNumber);
    Optional<CarDetails> findByUser(User user);



}
