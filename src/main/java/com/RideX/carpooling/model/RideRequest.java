package com.RideX.carpooling.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "rides_requests")
public class RideRequest {
    @Id
    private String requestId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference("rideRequestUserRef") // Back-reference to break the circular reference to User
    private User user;

    @ManyToOne
    @JoinColumn(name = "ride_id", nullable = false)
    @JsonBackReference("rideRequestRideRef") // Back-reference to break circular reference to Rides
    private Rides ride;

    private int requestedSeats;
    private int usedCoins;
    private double finalPrice;
    private boolean isCoDriver;
    private Date requestedAt;
    private String requestStatus;
    @PrePersist
    protected void onCreate() {
        requestedAt = new Date();
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Rides getRide() {
        return ride;
    }

    public void setRide(Rides ride) {
        this.ride = ride;
    }

    public int getRequestedSeats() {
        return requestedSeats;
    }

    public void setRequestedSeats(int requestedSeats) {
        this.requestedSeats = requestedSeats;
    }

    public boolean isCoDriver() {
        return isCoDriver;
    }

    public void setCoDriver(boolean coDriver) {
        isCoDriver = coDriver;
    }

    public Date getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(Date requestedAt) {
        this.requestedAt = requestedAt;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public int getUsedCoins() {
        return usedCoins;
    }

    public void setUsedCoins(int usedCoins) {
        this.usedCoins = usedCoins;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    @Override
    public String toString() {
        return "RideRequest{" +
                "requestId='" + requestId + '\'' +
                ", user=" + user +
                ", ride=" + ride +
                ", requestedSeats=" + requestedSeats +
                ", isCoDriver=" + isCoDriver +
                ", requestedAt=" + requestedAt +
                ", requestStatus='" + requestStatus + '\'' +
                '}';
    }
}
