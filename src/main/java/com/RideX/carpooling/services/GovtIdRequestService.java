package com.RideX.carpooling.services;

import com.RideX.carpooling.model.GovtIDVerifyRequest;

import java.util.List;

public interface GovtIdRequestService {
    void save(GovtIDVerifyRequest request);
    List<GovtIDVerifyRequest> getAllPendingRequest(boolean isVerified,boolean isUpdated);
    GovtIDVerifyRequest getPendingRequest(String requestId);
}
