package com.RideX.carpooling.services;

import com.RideX.carpooling.dto.Car_DTO_Details;

public interface CarDetailsServices {

    Car_DTO_Details findCarDetails(String userId);

}
