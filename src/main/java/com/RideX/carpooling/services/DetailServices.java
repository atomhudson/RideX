package com.RideX.carpooling.services;

import com.RideX.carpooling.dto.User_Details;
import com.RideX.carpooling.model.User;

public interface DetailServices {
    User_Details getUserDetails(User user);
}
