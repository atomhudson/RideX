package com.RideX.carpooling.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "rides")
public class Rides {
    @Id
    private String rideId;
    private String sourceCity;
    private String sourceAddress;
    private String sourceLat;
    private String sourceLon;
    private String destinationCity;
    private String destinationAddress;
    private String destinationLat;
    private String destinationLon;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    private double duration;
    private double distance;

    private int availableSeats;
    private int totalSeats;
    private double price;
    private boolean acAvailable;
    private boolean luggageAllowed;
    private boolean allowPets;
    private boolean allowSmoking;
    private String rideStatus;
    private String trackingToken;
    private String currentLat;
    private String currentLon;
    private Double currentHeading;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLocationUpdate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    protected void onPrePersist() {
        if (createdAt == null) createdAt = new Date();
        if (updatedAt == null) updatedAt = new Date();
        if (trackingToken == null || trackingToken.trim().isEmpty()) {
            trackingToken = java.util.UUID.randomUUID().toString().replace("-", "");
        }
    }

    @PreUpdate
    protected void onPreUpdate() {
        updatedAt = new Date();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", referencedColumnName = "userId", nullable = false)
    @JsonBackReference
    private User driver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false) // no unique = true
    private CarDetails car;

    @OneToMany(mappedBy = "ride", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference // Forward reference to serialize the list of RideRequest
    private List<RideRequest> rideRequests;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "ride_passengers",
            joinColumns = @JoinColumn(name = "ride_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> passengers;

    public String getRideId() {
        return rideId;
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;
    }

    public String getSourceCity() {
        return sourceCity;
    }

    public void setSourceCity(String sourceCity) {
        this.sourceCity = sourceCity;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        if (endTime == null && startTime != null) {
            double d = getDuration();
            if (d > 0) {
                return new Date(startTime.getTime() + (long)(d * 60 * 1000));
            }
        }
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAcAvailable() {
        return acAvailable;
    }

    public void setAcAvailable(boolean acAvailable) {
        this.acAvailable = acAvailable;
    }

    public boolean isLuggageAllowed() {
        return luggageAllowed;
    }

    public void setLuggageAllowed(boolean luggageAllowed) {
        this.luggageAllowed = luggageAllowed;
    }

    public boolean isAllowPets() {
        return allowPets;
    }

    public void setAllowPets(boolean allowPets) {
        this.allowPets = allowPets;
    }

    public boolean isAllowSmoking() {
        return allowSmoking;
    }

    public void setAllowSmoking(boolean allowSmoking) {
        this.allowSmoking = allowSmoking;
    }

    public String getRideStatus() {
        return rideStatus;
    }

    public void setRideStatus(String rideStatus) {
        this.rideStatus = rideStatus;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public CarDetails getCar() {
        return car;
    }

    public void setCar(CarDetails car) {
        this.car = car;
    }

    public List<User> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<User> passengers) {
        this.passengers = passengers;
    }

    public String getSourceLat() {
        return sourceLat;
    }

    public void setSourceLat(String sourceLat) {
        this.sourceLat = sourceLat;
    }

    public String getSourceLon() {
        return sourceLon;
    }

    public void setSourceLon(String sourceLon) {
        this.sourceLon = sourceLon;
    }

    public String getDestinationLat() {
        return destinationLat;
    }

    public void setDestinationLat(String destinationLat) {
        this.destinationLat = destinationLat;
    }

    public String getDestinationLon() {
        return destinationLon;
    }

    public void setDestinationLon(String destinationLon) {
        this.destinationLon = destinationLon;
    }

    public String getTrackingToken() {
        if (trackingToken == null || trackingToken.trim().isEmpty()) {
            trackingToken = java.util.UUID.randomUUID().toString().replace("-", "");
        }
        return trackingToken;
    }

    public void setTrackingToken(String trackingToken) {
        this.trackingToken = trackingToken;
    }

    public String getCurrentLat() {
        return currentLat != null && !currentLat.trim().isEmpty() ? currentLat : sourceLat;
    }

    public void setCurrentLat(String currentLat) {
        this.currentLat = currentLat;
    }

    public String getCurrentLon() {
        return currentLon != null && !currentLon.trim().isEmpty() ? currentLon : sourceLon;
    }

    public void setCurrentLon(String currentLon) {
        this.currentLon = currentLon;
    }

    public Double getCurrentHeading() {
        return currentHeading != null ? currentHeading : 0.0;
    }

    public void setCurrentHeading(Double currentHeading) {
        this.currentHeading = currentHeading;
    }

    public Date getLastLocationUpdate() {
        return lastLocationUpdate;
    }

    public void setLastLocationUpdate(Date lastLocationUpdate) {
        this.lastLocationUpdate = lastLocationUpdate;
    }

    public double getDuration() {
        if (duration <= 0) {
            if (startTime != null && endTime != null) {
                long diff = endTime.getTime() - startTime.getTime();
                if (diff > 0) {
                    return (double) (diff / (1000 * 60));
                }
            } else if (distance > 0) {
                return Math.round((distance / 55.0) * 60.0);
            }
        }
        return duration;
    }

    public String getFormattedDuration() {
        double d = getDuration();
        if (d <= 0) {
            return "";
        }
        int totalMinutes = (int) Math.round(d);
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;
        if (hours > 0 && minutes > 0) {
            return hours + "h " + minutes + "m";
        } else if (hours > 0) {
            return hours + "h";
        } else {
            return minutes + " min";
        }
    }

    public String getFormattedTime() {
        if (startTime == null) return "";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
        String startStr = sdf.format(startTime);
        Date end = getEndTime();
        if (end != null) {
            return startStr + " → " + sdf.format(end);
        }
        return startStr;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public List<RideRequest> getRideRequests() {
        return rideRequests;
    }

    public void setRideRequests(List<RideRequest> rideRequests) {
        this.rideRequests = rideRequests;
    }

    /** Returns true if at least one co-rider has an APPROVED booking */
    public boolean hasApprovedPassengers() {
        if (rideRequests == null) return false;
        return rideRequests.stream()
                .anyMatch(rr -> "APPROVED".equalsIgnoreCase(rr.getRequestStatus()));
    }

    /** Returns true if at least one approved co-rider has verified OTP (boarded) */
    public boolean hasBoardedPassengers() {
        if (rideRequests == null) return false;
        return rideRequests.stream()
                .anyMatch(RideRequest::isOtpVerified);
    }

    /** Returns total number of approved passengers */
    public int getApprovedPassengerCount() {
        if (rideRequests == null) return 0;
        return (int) rideRequests.stream()
                .filter(rr -> "APPROVED".equalsIgnoreCase(rr.getRequestStatus()))
                .count();
    }

    /** Returns total number of pending passenger requests */
    public int getPendingPassengerCount() {
        if (rideRequests == null) return 0;
        return (int) rideRequests.stream()
                .filter(rr -> "PENDING".equalsIgnoreCase(rr.getRequestStatus()))
                .count();
    }

    @Override
    public String toString() {
        return "Rides{" +
                "rideId='" + rideId + '\'' +
                ", sourceCity='" + sourceCity + '\'' +
                ", sourceAddress='" + sourceAddress + '\'' +
                ", sourceLat='" + sourceLat + '\'' +
                ", sourceLon='" + sourceLon + '\'' +
                ", destinationCity='" + destinationCity + '\'' +
                ", destinationAddress='" + destinationAddress + '\'' +
                ", destinationLat='" + destinationLat + '\'' +
                ", destinationLon='" + destinationLon + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", duration=" + duration +
                ", distance=" + distance +
                ", availableSeats=" + availableSeats +
                ", totalSeats=" + totalSeats +
                ", price=" + price +
                ", acAvailable=" + acAvailable +
                ", luggageAllowed=" + luggageAllowed +
                ", allowPets=" + allowPets +
                ", allowSmoking=" + allowSmoking +
                ", rideStatus='" + rideStatus + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
