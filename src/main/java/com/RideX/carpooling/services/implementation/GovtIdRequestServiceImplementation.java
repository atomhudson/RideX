package com.RideX.carpooling.services.implementation;


import com.RideX.carpooling.model.GovtIDVerifyRequest;
import com.RideX.carpooling.repositories.GovernmentIDVerifyRequestRepository;
import com.RideX.carpooling.services.GovtIdRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GovtIdRequestServiceImplementation implements GovtIdRequestService {

    @Autowired
    private GovernmentIDVerifyRequestRepository requestRepository;

    @Override
    public void save(GovtIDVerifyRequest request) {
        requestRepository.save(request);
    }

    @Override
    public List<GovtIDVerifyRequest> getAllPendingRequest(boolean isVerified,boolean isUpdated) {
        return requestRepository.getAllByVerifiedAndUpdated(isVerified, isUpdated);
    }

    @Override
    public GovtIDVerifyRequest getPendingRequest(String requestId) {
        return requestRepository.getById(requestId);
    }

}
