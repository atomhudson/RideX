package com.RideX.carpooling.services.implementation;

import com.RideX.carpooling.dto.Profile_Rides_Details;
import com.RideX.carpooling.model.RideRequest;
import com.RideX.carpooling.model.Rides;
import com.RideX.carpooling.repositories.RideRepository;
import com.RideX.carpooling.services.RideRequestServices;
import com.RideX.carpooling.services.RidesServices;
import com.RideX.carpooling.services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class RidesServicesImplementation implements RidesServices {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserServices userServices;

    @Autowired
    private RideRequestServices rideRequestServices;

    @Override
    public List<Rides> ridesCreated(String userId) {
        return rideRepository.findByDriverUserId(userId);
    }

    @Override
    public List<Rides> ridesJoined(String userId) {
        return userServices.getRidesJoinedByUser(userId);
    }

    @Override
    public Profile_Rides_Details ridesDetails(String userId) {
        Profile_Rides_Details ridesDetails = new Profile_Rides_Details();
        List<Rides> rides_created = ridesCreated(userId);
        List<Rides> rides_joined = ridesJoined(userId);
        List<RideRequest> ride_request = rideRequestServices.requestsCreated(userId);
        logger.info("User [{}] - Created: {}, Joined: {}, Requests Made: {}", userId, rides_created.size(), rides_joined.size(), ride_request.size());

        ridesDetails.setRidesCreated(rides_created);
        ridesDetails.setRidesJoined(rides_joined);
        ridesDetails.setRequestMade(ride_request);

        return ridesDetails;
    }

    @Override
    public Page<Rides> getAllRides(int page, int size, String sortField, String sortDirection) {
        Sort sort = sortDirection.equals("desc") ? Sort.by(sortField).descending() : Sort.by(sortDirection).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return rideRepository.findAll(pageable);
    }

    @Override
    public Page<Rides> getRidesByDate(Date date, int page, int size, String sortField, String sortDirection) {
        Sort sort = sortDirection.equals("desc") ? Sort.by(sortField).descending() : Sort.by(sortDirection).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return rideRepository.findByStartTimeBetween(date, date, pageable);
    }

    @Override
    public Rides getRideById(String rideId) {
        return rideRepository.findByRideId(rideId);
    }
}
