package com.RideX.carpooling.services;

import com.RideX.carpooling.dto.Profile_Rides_Details;
import com.RideX.carpooling.model.Rides;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface RidesServices {
    List<Rides> ridesCreated(String userId);
    List<Rides> ridesJoined(String userId);
    Profile_Rides_Details ridesDetails(String userId);

    Page<Rides> getAllRides(int page, int size, String sortField, String sortDirection);
    Page<Rides> getRidesByDate(Date date, int page, int size, String sortField, String sortDirection);
    Rides getRideById(String rideId);
}
