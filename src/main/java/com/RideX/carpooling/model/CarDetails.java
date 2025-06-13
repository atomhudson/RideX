package com.RideX.carpooling.model;

import com.RideX.carpooling.helpers.StringListConverter;
import jakarta.persistence.*;

import java.util.List;

@SuppressWarnings("JpaAttributeTypeInspection")
@Entity
@Table(name = "car_details")
public class CarDetails {
    @Id
    private String car_id;
    @Column(nullable = false)
    private String carMake;
    @Column(nullable = false)
    private String carModel;
    @Column(nullable = false)
    private String year;
    @Column(nullable = false)
    private String carType;
    @Column(nullable = false)
    private String color;
    @Column(unique = true, nullable = false)
    private String carNumber;
    @Column(nullable = false)
    private String fuelType;
    @Column(nullable = false)
    private int seat_available;
    @Column(nullable = false)
    private boolean ac_available;
    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<String> car_image_url;
    @Convert(converter = StringListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<String> cloudinaryImagePublicId;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false, unique = true)
    private User user;

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<String> getCar_image_url() {
        return car_image_url;
    }

    public void setCar_image_url(List<String> car_image_url) {
        this.car_image_url = car_image_url;
    }

    public List<String> getCloudinaryImagePublicId() {
        return cloudinaryImagePublicId;
    }

    public void setCloudinaryImagePublicId(List<String> cloudinaryImagePublicId) {
        this.cloudinaryImagePublicId = cloudinaryImagePublicId;
    }

    @Override
    public String toString() {
        return "CarDetails{" +
                "car_id='" + car_id + '\'' +
                ", carMake='" + carMake + '\'' +
                ", carModel='" + carModel + '\'' +
                ", year='" + year + '\'' +
                ", carType='" + carType + '\'' +
                ", color='" + color + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", seat_available=" + seat_available +
                ", ac_available=" + ac_available +
                ", car_image_url='" + car_image_url + '\'' +
                '}';
    }

    public CarDetails() {}
}
