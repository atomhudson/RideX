package com.RideX.carpooling.forms;

import java.util.List;

public class RideForm {
    private String rideId;
    private String fromAddress;
    private String fromCity;
    private String fromLat;
    private String fromLng;
    private String toAddress;
    private String toCity;
    private String toLat;
    private String toLng;
    private List<String> available_car;
    private int seat;
    private int price;
    private String departure_time;
    private boolean petsAllowed;
    private boolean smokingAllowed;
    private boolean ac_available;
    private boolean luggage_allowed;

    public String getRideId() {
        return rideId;
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getFromLat() {
        return fromLat;
    }

    public void setFromLat(String fromLat) {
        this.fromLat = fromLat;
    }

    public String getFromLng() {
        return fromLng;
    }

    public void setFromLng(String fromLng) {
        this.fromLng = fromLng;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public String getToLat() {
        return toLat;
    }

    public void setToLat(String toLat) {
        this.toLat = toLat;
    }

    public String getToLng() {
        return toLng;
    }

    public void setToLng(String toLng) {
        this.toLng = toLng;
    }

    public List<String> getAvailable_car() {
        return available_car;
    }

    public void setAvailable_car(List<String> available_car) {
        this.available_car = available_car;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    public boolean isPetsAllowed() {
        return petsAllowed;
    }

    public void setPetsAllowed(boolean petsAllowed) {
        this.petsAllowed = petsAllowed;
    }

    public boolean isSmokingAllowed() {
        return smokingAllowed;
    }

    public void setSmokingAllowed(boolean smokingAllowed) {
        this.smokingAllowed = smokingAllowed;
    }

    public boolean isAc_available() {
        return ac_available;
    }

    public void setAc_available(boolean ac_available) {
        this.ac_available = ac_available;
    }

    public boolean isLuggage_allowed() {
        return luggage_allowed;
    }

    public void setLuggage_allowed(boolean luggage_allowed) {
        this.luggage_allowed = luggage_allowed;
    }

    @Override
    public String toString() {
        return "RideForm{" +
                "rideId='" + rideId + '\'' +
                ", fromAddress='" + fromAddress + '\'' +
                ", fromCity='" + fromCity + '\'' +
                ", fromLat='" + fromLat + '\'' +
                ", fromLng='" + fromLng + '\'' +
                ", toAddress='" + toAddress + '\'' +
                ", toCity='" + toCity + '\'' +
                ", toLat='" + toLat + '\'' +
                ", toLng='" + toLng + '\'' +
                ", available_car=" + available_car +
                ", seat=" + seat +
                ", price=" + price +
                ", departure_time='" + departure_time + '\'' +
                ", petsAllowed=" + petsAllowed +
                ", smokingAllowed=" + smokingAllowed +
                ", ac_available=" + ac_available +
                ", luggage_allowed=" + luggage_allowed +
                '}';
    }
}
