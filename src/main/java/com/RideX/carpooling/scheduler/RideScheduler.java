package com.RideX.carpooling.scheduler;

import com.RideX.carpooling.helpers.AppConstants;
import com.RideX.carpooling.model.Rides;
import com.RideX.carpooling.repositories.RideRepository;
import com.RideX.carpooling.services.EmailServices;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

/**
 * RideScheduler — Phase 2 Revision
 * ---------------------------------
 * The scheduler no longer blindly flips ride statuses based on clock time.
 *
 * Responsibility now split:
 *  - Ride ONGOING  → triggered by driver verifying passenger OTP  (RideController#verifyStartOtp)
 *  - Ride COMPLETED → triggered by driver clicking "Complete Ride" (RideController#completeRide)
 *
 * The scheduler retains two lightweight tasks:
 *  1. Auto-cancel PLANNED rides that are 2+ hours past their start time with zero boardings
 *     (nobody showed up on either side, clearly abandoned).
 *  2. Log a warning for ONGOING rides that have run 60+ minutes past their estimated end time
 *     (driver forgot to mark complete — ops team can investigate).
 */
@Component
public class RideScheduler {

    private final Logger logger = LoggerFactory.getLogger(RideScheduler.class);

    private final RideRepository ridesRepository;
    private final EmailServices emailServices;

    public RideScheduler(RideRepository ridesRepository, EmailServices emailServices) {
        this.ridesRepository = ridesRepository;
        this.emailServices   = emailServices;
    }

    /**
     * Every 10 minutes: auto-cancel PLANNED rides that started 2+ hours ago
     * and had no passengers board (otpVerified == false for all approved requests).
     * These are clearly abandoned rides — no driver/passenger showed up.
     */
    @Scheduled(fixedRate = 600_000) // every 10 minutes
    @Transactional
    public void autoCancelAbandonedRides() {
        Date twoHoursAgo = Date.from(Instant.now().minus(2, ChronoUnit.HOURS));

        List<Rides> staleRides = ridesRepository.findByRideStatusAndStartTimeBefore(
                AppConstants.RIDE_PLANED, twoHoursAgo);

        for (Rides ride : staleRides) {
            boolean anyBoarded = ride.getRideRequests() != null &&
                ride.getRideRequests().stream()
                    .anyMatch(rr -> "APPROVED".equals(rr.getRequestStatus()) && rr.isOtpVerified());

            if (!anyBoarded) {
                ride.setRideStatus(AppConstants.RIDE_CANCELLED);
                ride.setUpdatedAt(new Date());
                logger.warn("Auto-cancelled abandoned ride {} (started {} with zero boardings).",
                        ride.getRideId(), ride.getStartTime());

                // Notify driver
                try {
                    emailServices.sendEmail(
                        ride.getDriver().getEmail(),
                        "Your RideX ride was auto-cancelled",
                        "Your ride from " + ride.getSourceCity() + " to " + ride.getDestinationCity()
                        + " was automatically cancelled because it started 2+ hours ago with no passengers boarding."
                        + "\n\nIf this is an error, please contact support."
                    );
                } catch (Exception e) {
                    logger.warn("Auto-cancel email failed for ride {}: {}", ride.getRideId(), e.getMessage());
                }
            }
        }

        ridesRepository.saveAll(staleRides);
    }

    /**
     * Every 30 minutes: log a warning for ONGOING rides that ran 60+ min past their estimated end time.
     * Does NOT auto-complete — the driver must click "Complete Ride" in the app.
     */
    @Scheduled(fixedRate = 1_800_000) // every 30 minutes
    @Transactional
    public void flagOverdueOngoingRides() {
        Date oneHourAgo = Date.from(Instant.now().minus(60, ChronoUnit.MINUTES));

        List<Rides> overdueRides = ridesRepository.findByRideStatusAndEndTimeBefore(
                AppConstants.RIDE_ONGOING, oneHourAgo);

        for (Rides ride : overdueRides) {
            logger.warn("Overdue ONGOING ride detected: {} ({} → {}). Driver has not marked it complete.",
                    ride.getRideId(), ride.getSourceCity(), ride.getDestinationCity());
        }

        if (!overdueRides.isEmpty()) {
            logger.info("Found {} overdue ONGOING ride(s). No auto-action taken — driver must complete manually.",
                    overdueRides.size());
        }
    }
}
