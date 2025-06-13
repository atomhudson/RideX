package com.RideX.carpooling.services;

import com.RideX.carpooling.model.Rides;
import com.RideX.carpooling.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserServices {
    User saveUser(User user);
    User getUserByEmail(String email);
    User getUserById(String id);

    List<Rides> getRidesJoinedByUser(String userId);
    List<User> getUsers();
    List<User> verifiedEmail(boolean isEmailVerified);
    List<User> enabledUser(boolean isEnabled);

    Page<User> getAllUsers(int page, int size, String sortField, String sortDirection);
    Page<User> searchByUserId(String userId, int page, int size, String sortField, String sortDirection);
    Page<User> searchByEmail(String email, int page, int size, String sortField, String sortDirection);
    Page<User> searchByPhone(String phone, int page, int size, String sortField, String sortDirection);

}
