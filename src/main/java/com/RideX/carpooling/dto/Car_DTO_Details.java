package com.RideX.carpooling.dto;

public class Car_DTO_Details {
    private String carMake;
    private String carModel;
    private String year;
    private String carType;
    private String color;
    private String carNumber;
    private String fuelType;
    private int seat_available;
    private boolean ac_available;
    private String car_Image_Url;


    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public int getSeat_available() {
        return seat_available;
    }

    public void setSeat_available(int seat_available) {
        this.seat_available = seat_available;
    }

    public boolean isAc_available() {
        return ac_available;
    }

    public void setAc_available(boolean ac_available) {
        this.ac_available = ac_available;
    }


    public String getCar_Image_Url() {
        return car_Image_Url;
    }

    public void setCar_Image_Url(String car_Image_Url) {
        this.car_Image_Url = car_Image_Url;
    }
}
