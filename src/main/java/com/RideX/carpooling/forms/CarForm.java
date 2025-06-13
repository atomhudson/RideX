package com.RideX.carpooling.forms;

import com.RideX.carpooling.validators.ValidFile;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class CarForm {

    @NotBlank(message = "Please select the car manufacturer.")
    private String carMake;

    @NotBlank(message = "Please choose the car model.")
    private String carModel;

    @NotBlank(message = "Year of registration is required. Please select a valid year.")
    private String year;

    @NotBlank(message = "Please specify the type of your car (e.g., Sedan, SUV).")
    private String carType;

    @NotBlank(message = "Please enter the color of your car.")
    private String color;

    @NotBlank(message = "Please select the fuel type (e.g., Petrol, Diesel, Electric).")
    private String fuelType;

    @NotBlank(message = "Please enter your car's registration number.")
    private String carNumber;

    private Integer seat_available;


    private boolean ac_available = false;

    @NotEmpty(message = "Please upload at least one clear image of your car.")
    @Size(max = 3, message = "You can upload a maximum of 3 car images.")
    @ValidFile(message = "Each image must be non-empty and less than 2MB.")
    private List<MultipartFile> carImages;

    public @NotBlank(message = "Please select the car manufacturer.") String getCarMake() {
        return carMake;
    }

    public void setCarMake(@NotBlank(message = "Please select the car manufacturer.") String carMake) {
        this.carMake = carMake;
    }

    public @NotBlank(message = "Please choose the car model.") String getCarModel() {
        return carModel;
    }

    public void setCarModel(@NotBlank(message = "Please choose the car model.") String carModel) {
        this.carModel = carModel;
    }

    public @NotBlank(message = "Year of registration is required. Please select a valid year.") String getYear() {
        return year;
    }

    public void setYear(@NotBlank(message = "Year of registration is required. Please select a valid year.") String year) {
        this.year = year;
    }

    public @NotBlank(message = "Please specify the type of your car (e.g., Sedan, SUV).") String getCarType() {
        return carType;
    }

    public void setCarType(@NotBlank(message = "Please specify the type of your car (e.g., Sedan, SUV).") String carType) {
        this.carType = carType;
    }

    public @NotBlank(message = "Please enter the color of your car.") String getColor() {
        return color;
    }

    public void setColor(@NotBlank(message = "Please enter the color of your car.") String color) {
        this.color = color;
    }

    public @NotBlank(message = "Please select the fuel type (e.g., Petrol, Diesel, Electric).") String getFuelType() {
        return fuelType;
    }

    public void setFuelType(@NotBlank(message = "Please select the fuel type (e.g., Petrol, Diesel, Electric).") String fuelType) {
        this.fuelType = fuelType;
    }

    public @NotBlank(message = "Please enter your car's registration number.") String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(@NotBlank(message = "Please enter your car's registration number.") String carNumber) {
        this.carNumber = carNumber;
    }

    public Integer getSeat_available() {
        return seat_available == null ? 0 : seat_available;
    }

    public void setSeat_available(Integer seat_available) {
        this.seat_available = seat_available;
    }

    public @NotEmpty(message = "Please upload at least one clear image of your car.") @Size(max = 3, message = "You can upload a maximum of 3 car images.") List<MultipartFile> getCarImages() {
        return carImages;
    }

    public void setCarImages(@NotEmpty(message = "Please upload at least one clear image of your car.") @Size(max = 3, message = "You can upload a maximum of 3 car images.") List<MultipartFile> carImages) {
        this.carImages = carImages;
    }

    public boolean isAc_available() {
        return ac_available;
    }

    public void setAc_available(boolean ac_available) {
        this.ac_available = ac_available;
    }

    @Override
    public String toString() {
        return "CarForm{" +
                "carMake='" + carMake + '\'' +
                ", carModel='" + carModel + '\'' +
                ", year='" + year + '\'' +
                ", carType='" + carType + '\'' +
                ", color='" + color + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", seat_available=" + seat_available +
                ", ac_available=" + ac_available +
                ", carImages=" + carImages +
                '}';
    }
}
