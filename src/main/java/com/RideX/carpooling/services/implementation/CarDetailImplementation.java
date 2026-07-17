package com.RideX.carpooling.services.implementation;

import com.RideX.carpooling.dto.Car_DTO_Details;
import com.RideX.carpooling.model.CarDetails;
import com.RideX.carpooling.model.User;
import com.RideX.carpooling.repositories.CarDetailsRepository;
import com.RideX.carpooling.services.CarDetailsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarDetailImplementation implements CarDetailsServices {

    @Autowired
    private CarDetailsRepository carDetailsRepository;

    @Override
    public Car_DTO_Details findCarDetails(User user) {
        return toDto(user.getCarDetails());
    }

    @Override
    public Car_DTO_Details findCarDetails(String userId) {
        return carDetailsRepository.findByUser_UserId(userId)
                .map(this::toDto)
                .orElseGet(Car_DTO_Details::new);
    }

    private Car_DTO_Details toDto(CarDetails carDetail) {
        if (carDetail == null) {
            return new Car_DTO_Details();
        }
        Car_DTO_Details carDetailDTO = new Car_DTO_Details();
        carDetailDTO.setCarMake(carDetail.getCarMake());
        carDetailDTO.setCarModel(carDetail.getCarModel());
        carDetailDTO.setYear(carDetail.getYear());
        carDetailDTO.setCarType(carDetail.getCarType());
        carDetailDTO.setColor(carDetail.getColor());
        carDetailDTO.setFuelType(carDetail.getFuelType());
        carDetailDTO.setSeat_available(carDetail.getSeat_available());
        carDetailDTO.setCarNumber(carDetail.getCarNumber());
        carDetailDTO.setAc_available(carDetail.isAc_available());
        carDetailDTO.setCar_Image_Url(carDetail.getCar_image_url().get(0));
        return carDetailDTO;
    }
}
