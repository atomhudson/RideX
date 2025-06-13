package com.RideX.carpooling.services;

import com.RideX.carpooling.model.RideRequest;

import java.util.List;

public interface RideRequestServices {
    List<RideRequest> requestsCreated(String userId);
}
