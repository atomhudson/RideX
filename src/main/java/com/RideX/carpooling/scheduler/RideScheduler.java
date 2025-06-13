package com.RideX.carpooling.scheduler;

import com.RideX.carpooling.controller.RideController;
import com.RideX.carpooling.helpers.AppConstants;
import com.RideX.carpooling.model.Rides;
import com.RideX.carpooling.model.User;
import com.RideX.carpooling.repositories.RideRepository;
import com.RideX.carpooling.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class RideScheduler {

    private final Logger logger = LoggerFactory.getLogger(RideScheduler.class);

    private final RideRepository ridesRepository;
    private final UserRepository userRepository;

    public RideScheduler(RideRepository ridesRepository, UserRepository userRepository) {
        this.ridesRepository = ridesRepository;
        this.userRepository = userRepository;
    }

    // Runs every 1 minute
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void updateRideStatuses() {
        Date now = new Date();

        List<Rides> ridesToUpdate = ridesRepository.findByRideStatusAndStartTimeBefore("PLANNED", now);

        logger.info("Updating rides statuses");
        logger.info("Rides to update: {}",ridesToUpdate.size());


        for (Rides ride : ridesToUpdate) {
            ride.setRideStatus(AppConstants.RIDE_ONGOING);
            ride.setUpdatedAt(now);
            logger.info("Ride Id from PLANNED TO ONGOING: {}", ride.getRideId());
        }

        List<Rides> ongoingRides = ridesRepository.findByRideStatusAndEndTimeBefore("ONGOING", now);
        for (Rides ride : ongoingRides) {
            ride.setRideStatus(AppConstants.RIDE_COMPLETED);
            ride.setUpdatedAt(now);

            double distance = ride.getDistance();
            int rewardCoins = (int) Math.round(distance * 0.10);
            User driver = ride.getDriver();

            // Reward driver
            driver.setCoins(driver.getCoins() + rewardCoins + 5);
            userRepository.save(driver);

            // Reward passengers
            if (ride.getRideRequests() != null) {
                ride.getRideRequests().stream()
                        .filter(req -> "APPROVED".equals(req.getRequestStatus()) && req.isCoDriver())
                        .forEach(coDriverRequest -> {
                            User coDriver = coDriverRequest.getUser();
                            coDriver.setCoins(coDriver.getCoins() + rewardCoins);
                            userRepository.save(coDriver);
                        });
            }

            logger.info("Ride Id from ONGOING TO COMPLETED: {}", ride.getRideId());
        }
        ridesRepository.saveAll(ridesToUpdate);
        ridesRepository.saveAll(ongoingRides);
        logger.info("Ride statuses updated");
    }
}

