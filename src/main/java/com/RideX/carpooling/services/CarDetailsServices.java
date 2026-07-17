package com.RideX.carpooling.services;

import com.RideX.carpooling.dto.Car_DTO_Details;
import com.RideX.carpooling.model.User;

public interface CarDetailsServices {

    Car_DTO_Details findCarDetails(User user);

    Car_DTO_Details findCarDetails(String userId);

}
