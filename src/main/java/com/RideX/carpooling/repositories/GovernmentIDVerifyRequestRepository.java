package com.RideX.carpooling.repositories;

import com.RideX.carpooling.model.GovtIDVerifyRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GovernmentIDVerifyRequestRepository extends JpaRepository<GovtIDVerifyRequest, String> {
    List<GovtIDVerifyRequest> getAllByVerifiedAndUpdated(boolean verified, boolean updated);
}
