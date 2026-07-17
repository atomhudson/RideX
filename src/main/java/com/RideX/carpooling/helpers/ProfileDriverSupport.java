package com.RideX.carpooling.helpers;

import com.RideX.carpooling.model.RideRequest;
import com.RideX.carpooling.model.Rides;
import com.RideX.carpooling.model.User;
import com.RideX.carpooling.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Component
public class ProfileDriverSupport {

    @Autowired
    private UserRepository userRepository;

    public void attachPublicDriverProfiles(List<Rides> rides) {
        if (rides == null || rides.isEmpty()) {
            return;
        }

        Set<String> driverIds = new HashSet<>();
        for (Rides ride : rides) {
            User driver = ride.getDriver();
            if (driver != null && driver.getUserId() != null) {
                driverIds.add(driver.getUserId());
            }
        }

        if (driverIds.isEmpty()) {
            return;
        }

        Map<String, User> profiles = loadPublicProfiles(driverIds);
        for (Rides ride : rides) {
            User driver = ride.getDriver();
            if (driver == null) {
                continue;
            }
            User profile = profiles.get(driver.getUserId());
            if (profile != null) {
                ride.setDriver(profile);
            }
        }
    }

    public void attachPublicDriverProfilesToRequests(List<RideRequest> requests) {
        if (requests == null || requests.isEmpty()) {
            return;
        }

        List<Rides> rides = requests.stream()
                .map(RideRequest::getRide)
                .filter(Objects::nonNull)
                .toList();
        attachPublicDriverProfiles(rides);
    }

    private Map<String, User> loadPublicProfiles(Set<String> userIds) {
        Map<String, User> profiles = new HashMap<>();
        for (Object[] row : userRepository.findPublicProfilesByUserIds(userIds)) {
            User user = toPublicProfile(row);
            profiles.put(user.getUserId(), user);
        }
        return profiles;
    }

    private User toPublicProfile(Object[] row) {
        User user = new User();
        user.setUserId((String) row[0]);
        user.setfName((String) row[1]);
        user.setlName((String) row[2]);
        user.setProfilePic((String) row[3]);
        return user;
    }
}
