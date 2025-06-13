package com.RideX.carpooling.repositories;

import com.RideX.carpooling.model.Details;
import com.RideX.carpooling.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DetailsRepository extends JpaRepository<Details, String> {
    Optional<Details> findByUser(User user);
}
