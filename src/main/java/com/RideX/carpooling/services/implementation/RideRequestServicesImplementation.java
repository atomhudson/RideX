package com.RideX.carpooling.services.implementation;

import com.RideX.carpooling.model.RideRequest;
import com.RideX.carpooling.repositories.RidesRequestsRepository;
import com.RideX.carpooling.services.RideRequestServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideRequestServicesImplementation implements RideRequestServices {

    @Autowired
    private RidesRequestsRepository ridesRequestsRepository;

    @Override
    public List<RideRequest> requestsCreated(String userId) {
        return ridesRequestsRepository.findAllByUser_UserId(userId);
    }
}
