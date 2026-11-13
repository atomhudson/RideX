package com.RideX.carpooling.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "rides_requests")
public class RideRequest {
    @Id
    private String requestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference("rideRequestUserRef") // Back-reference to break the circular reference to User
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ride_id", nullable = false)
    @JsonBackReference("rideRequestRideRef") // Back-reference to break circular reference to Rides
    private Rides ride;

    private int requestedSeats;
    private int usedCoins;
    private double finalPrice;
    private boolean isCoDriver;
    private Date requestedAt;
    private String requestStatus;

    // ── OTP & Payment fields (Phase 2 & 3) ──────────────────────────
    /** 4-digit boarding OTP shown to passenger; driver enters it to start the ride */
    private String startOtp;

    /** True once the driver has verified the passenger's OTP at pickup */
    private boolean otpVerified;

    /** UNPAID | PAID | REFUNDED — for Razorpay integration (Phase 3) */
    private String paymentStatus = "UNPAID";

    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;

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

    public String getStartOtp() { return startOtp; }
    public void setStartOtp(String startOtp) { this.startOtp = startOtp; }

    public boolean isOtpVerified() { return otpVerified; }
    public void setOtpVerified(boolean otpVerified) { this.otpVerified = otpVerified; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getRazorpayOrderId() { return razorpayOrderId; }
    public void setRazorpayOrderId(String razorpayOrderId) { this.razorpayOrderId = razorpayOrderId; }

    public String getRazorpayPaymentId() { return razorpayPaymentId; }
    public void setRazorpayPaymentId(String razorpayPaymentId) { this.razorpayPaymentId = razorpayPaymentId; }

    public String getRazorpaySignature() { return razorpaySignature; }
    public void setRazorpaySignature(String razorpaySignature) { this.razorpaySignature = razorpaySignature; }

    @Override
    public String toString() {
        return "RideRequest{" +
                "requestId='" + requestId + '\'' +
                ", user=" + user +
                ", requestedSeats=" + requestedSeats +
                ", isCoDriver=" + isCoDriver +
                ", requestedAt=" + requestedAt +
                ", requestStatus='" + requestStatus + '\'' +
                ", otpVerified=" + otpVerified +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}
